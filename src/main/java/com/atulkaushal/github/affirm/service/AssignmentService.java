package com.atulkaushal.github.affirm.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.atulkaushal.github.affirm.writer.AssignmentsWriter;

/** The Class AssignmentService. */
public class AssignmentService {

  /** The loan assignment. */
  Map<Integer, Integer> loanAssignment = new LinkedHashMap<Integer, Integer>();

  /**
   * Adds the loan info.
   *
   * @param loanId the loan id
   * @param facilityId the facility id
   */
  public void addLoanInfo(Integer loanId, Integer facilityId) {
    loanAssignment.put(loanId, facilityId);
  }

  /**
   * Generate report.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void generateReport() throws IOException {
    AssignmentsWriter assignWriter = new AssignmentsWriter();
    assignWriter.generateAssignmentFile(loanAssignment);
  }
}
