package com.atulkaushal.github.affirm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.atulkaushal.github.affirm.model.Loan;
import com.atulkaushal.github.affirm.service.AssignmentService;
import com.atulkaushal.github.affirm.service.FacilityService;
import com.atulkaushal.github.affirm.service.LoanService;
import com.atulkaushal.github.affirm.service.YeildsService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

/** The Class Main1. */
public class Main {

  /** The Constant LOANS_CSV. */
  private static final String LOANS_CSV = "loans.csv";

  /** The Constant COVENANTS_CSV. */
  private static final String COVENANTS_CSV = "covenants.csv";

  /** The Constant FACILITIES_CSV. */
  private static final String FACILITIES_CSV = "facilities.csv";

  /** The facility service. */
  static FacilityService facilityService;

  /** The assignment service. */
  static AssignmentService assignmentService;

  /** The yeild service. */
  static YeildsService yeildsService;

  /**
   * Inits the.
   *
   * @throws CsvValidationException the csv validation exception
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void init() throws CsvValidationException, FileNotFoundException, IOException {
    facilityService = new FacilityService(FACILITIES_CSV, COVENANTS_CSV);
    assignmentService = new AssignmentService();
    yeildsService = new YeildsService();
  }
  /**
   * The main method.
   *
   * @param args the arguments
   * @throws CsvValidationException the csv validation exception
   * @throws NumberFormatException the number format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void main(String[] args)
      throws CsvValidationException, NumberFormatException, IOException {
    init();

    List<Loan> loans = loadLoanData();
    LoanService loanService = new LoanService(facilityService, assignmentService, yeildsService);
    for (Loan loan : loans) {
      loanService.processLoan(loan);
    }
    generateReports();
  }

  /**
   * Generate reports.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void generateReports() throws IOException {
    assignmentService.generateReport();
    yeildsService.generateReport();
  }

  /**
   * Load loan data.
   *
   * @return the list
   * @throws CsvValidationException the csv validation exception
   * @throws NumberFormatException the number format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static List<Loan> loadLoanData()
      throws CsvValidationException, NumberFormatException, IOException {
    List<Loan> loans = new ArrayList<>();
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File loanFile = new File(classloader.getResource(LOANS_CSV).getFile());

    CSVReader loanFileReader =
        new CSVReaderBuilder(new FileReader(loanFile)).withSkipLines(1).build();
    String[] line2;
    while ((line2 = loanFileReader.readNext()) != null) {
      Loan loan =
          new Loan(
              Integer.valueOf(line2[2]),
              new BigDecimal(line2[1]),
              new BigDecimal(line2[0]),
              new BigDecimal(line2[3]),
              String.valueOf(line2[4]));
      loans.add(loan);
    }
    loanFileReader.close();
    return loans;
  }
}
