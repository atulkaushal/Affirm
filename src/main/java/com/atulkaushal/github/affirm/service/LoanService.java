package com.atulkaushal.github.affirm.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import com.atulkaushal.github.affirm.model.Facility;
import com.atulkaushal.github.affirm.model.Loan;

/**
 * The Class LoanService.
 *
 * @author Atul
 */
public class LoanService {

  /** The facility service. */
  FacilityService facilityService;

  /** The assignment service. */
  AssignmentService assignmentService;

  /** The yeild service. */
  YeildsService yeildsService;

  /**
   * Instantiates a new loan service.
   *
   * @param facilityService the facility service
   * @param assignmentService the assignment service
   * @param yeildsService the yeilds service
   */
  public LoanService(
      FacilityService facilityService,
      AssignmentService assignmentService,
      YeildsService yeildsService) {
    this.facilityService = facilityService;
    this.assignmentService = assignmentService;
    this.yeildsService = yeildsService;
  }

  /**
   * Process loan.
   *
   * @param loan the loan
   * @return true, if successful
   */
  public boolean processLoan(Loan loan) {
    // System.out.println("Starting processing for loan id: " + loan.getId());
    Iterator<BigDecimal> iterator =
        facilityService.getFacilitiesSortedByInterestRate().keySet().iterator();

    while (iterator.hasNext()) {
      BigDecimal key = iterator.next();
      List<Facility> facilities = facilityService.getFacilitiesSortedByInterestRate().get(key);
      facilities.sort((first, next) -> (next.getAmount().compareTo(first.getAmount())));
      for (Facility facility : facilities) {
        // Compare interest rates
        if (facility.getInterestRate().compareTo(loan.getInterestRate()) <= 0) {
          // Check if facility have funds.
          int num = facility.getAmount().compareTo(loan.getAmount());
          // Check if Loan passed the Default likelihood and banned state criteria
          if (num >= 0
              && facility.getCovenant().isAllowed(loan.getDefaultLikelihood(), loan.getState())) {
            facility.setAmount(facility.getAmount().subtract(loan.getAmount()));
            assignmentService.addLoanInfo(loan.getId(), facility.getId());

            BigDecimal expectedYeild = calculateExpectedYeild(loan, facility);
            if (expectedYeild.signum() >= 0)
              yeildsService.addYeildsInfo(facility.getId(), expectedYeild);
            return true;
          }
        }
      }
    }
    assignmentService.addLoanInfo(loan.getId(), 0);
    return false;
  }

  /**
   * Calculate expected yeild.
   *
   * @param loan the loan
   * @param facility the facility
   * @return the big decimal
   */
  private BigDecimal calculateExpectedYeild(Loan loan, Facility facility) {
    BigDecimal temp1 =
        (BigDecimal.ONE.subtract(loan.getDefaultLikelihood()))
            .multiply(loan.getInterestRate())
            .multiply(loan.getAmount());
    BigDecimal temp2 = loan.getDefaultLikelihood().multiply(loan.getAmount());
    BigDecimal temp3 = facility.getInterestRate().multiply(loan.getAmount());
    BigDecimal expectedYeild = temp1.subtract(temp2).subtract(temp3);
    return expectedYeild;
  }
}
