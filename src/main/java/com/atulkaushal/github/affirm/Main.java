package com.atulkaushal.github.affirm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.atulkaushal.github.affirm.model.Covenant;
import com.atulkaushal.github.affirm.model.Facility;
import com.atulkaushal.github.affirm.model.Loan;
import com.atulkaushal.github.affirm.writer.AssignmentsWriter;
import com.atulkaushal.github.affirm.writer.YeildsWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

/**
 * The Class Main.
 *
 * @author Atul
 */
public class Main {

  /** The Constant LOANS_CSV. */
  private static final String LOANS_CSV = "loans1.csv";

  /** The Constant COVENANTS_CSV. */
  private static final String COVENANTS_CSV = "covenants1.csv";

  /** The Constant FACILITIES_CSV. */
  private static final String FACILITIES_CSV = "facilities1.csv";

  /** The Constant facilities. */
  static final Map<Integer, Facility> facilities = new HashMap<Integer, Facility>();

  /** The facilities expected yeild map. */
  static Map<Integer, BigDecimal> facilitiesExpectedYeildMap = new HashMap<Integer, BigDecimal>();

  /** The loans. */
  static List<Loan> loans = new ArrayList<Loan>();

  /** The facilities by interest rate. */
  static SortedMap<BigDecimal, List<Facility>> facilitiesByInterestRate =
      new TreeMap<BigDecimal, List<Facility>>();

  /** The bank level covenant. */
  static Map<Integer, Covenant> bankLevelCovenant = new HashMap<>();

  /** The bank facilities map. */
  static Map<Integer, List<Facility>> bankFacilitiesMap = new HashMap<>();

  /** The loan assignment. */
  static Map<Integer, Integer> loanAssignment = new LinkedHashMap<Integer, Integer>();

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

    loadCSVFiles();

    for (Loan loan : loans) {
      processLoan(loan);
    }
    AssignmentsWriter assignWriter = new AssignmentsWriter();
    assignWriter.generateAssignmentFile(loanAssignment);

    YeildsWriter yeildsWriter = new YeildsWriter();
    yeildsWriter.generateYeildsFile(facilitiesExpectedYeildMap);
  }

  /**
   * Process loan.
   *
   * @param loan the loan
   * @return true, if successful
   */
  private static boolean processLoan(Loan loan) {

    System.out.println("Starting processing for loan id: " + loan.getId());
    Iterator<BigDecimal> iterator = facilitiesByInterestRate.keySet().iterator();

    while (iterator.hasNext()) {
      BigDecimal key = iterator.next();
      List<Facility> facilities = facilitiesByInterestRate.get(key);
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
            loanAssignment.put(loan.getId(), facility.getId());

            BigDecimal temp1 =
                (BigDecimal.ONE.subtract(loan.getDefaultLikelihood()))
                    .multiply(loan.getInterestRate())
                    .multiply(loan.getAmount());
            BigDecimal temp2 = loan.getDefaultLikelihood().multiply(loan.getAmount());
            BigDecimal temp3 = facility.getInterestRate().multiply(loan.getAmount());
            BigDecimal expectedYeild = temp1.subtract(temp2).subtract(temp3);

            if (expectedYeild.signum() >= 0) {
              if (facilitiesExpectedYeildMap.containsKey(facility.getId())) {
                BigDecimal value = facilitiesExpectedYeildMap.get(facility.getId());
                value = value.add(expectedYeild);
                facilitiesExpectedYeildMap.put(facility.getId(), value);
              } else {
                facilitiesExpectedYeildMap.put(facility.getId(), expectedYeild);
              }
            }
            return true;
          }
        }
      }
    }
    // System.out.println("Loan failed for Loan id: " + loan.getId());
    loanAssignment.putIfAbsent(loan.getId(), 0);

    return false;
  }

  /**
   * Load CSV files.
   *
   * @throws CsvValidationException the csv validation exception
   * @throws NumberFormatException the number format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void loadCSVFiles()
      throws CsvValidationException, NumberFormatException, IOException {
    // File bankFile = new File(Main.class.getResource("banks.csv").getFile()); //No need of this
    // unless you need Bank name.

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File facilityFile = new File(classloader.getResource(FACILITIES_CSV).getFile());
    File covenantFile = new File(classloader.getResource(COVENANTS_CSV).getFile());

    loadFacilityCSV(facilityFile);

    loadCovenantCSV(covenantFile);

    loadLoansCSV(classloader);
  }

  /**
   * Load facility CSV.
   *
   * @param facilityFile the facility file
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CsvValidationException the csv validation exception
   */
  private static void loadFacilityCSV(File facilityFile)
      throws FileNotFoundException, IOException, CsvValidationException {
    CSVReader facilityFileReader =
        new CSVReaderBuilder(new FileReader(facilityFile)).withSkipLines(1).build();
    String[] line;
    while ((line = facilityFileReader.readNext()) != null) {
      Facility facility =
          new Facility(
              Integer.parseInt(line[3]),
              Integer.parseInt(line[2]),
              new BigDecimal(line[1]),
              new BigDecimal(line[0]),
              new Covenant());
      facilities.put(facility.getId(), facility);
      facilitiesByInterestRate.putIfAbsent(facility.getInterestRate(), new ArrayList<Facility>());
      facilitiesByInterestRate.get(facility.getInterestRate()).add(facility);
    }
    facilityFileReader.close();
  }

  /**
   * Load covenant CSV.
   *
   * @param covenantFile the covenant file
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CsvValidationException the csv validation exception
   */
  private static void loadCovenantCSV(File covenantFile)
      throws FileNotFoundException, IOException, CsvValidationException {
    CSVReader covenantFileReader =
        new CSVReaderBuilder(new FileReader(covenantFile)).withSkipLines(1).build();
    String[] line1;
    while ((line1 = covenantFileReader.readNext()) != null) {
      if (line1[0].isEmpty()) {
        int bankId = Integer.parseInt(line1[2]);
        bankLevelCovenant.putIfAbsent(bankId, new Covenant());
        Covenant covenant = bankLevelCovenant.get(bankId);
        if (!line1[1].isEmpty()) {
          covenant.setMaxDefaultLikelihood(new BigDecimal(line1[1]));
        }
        if (!line1[3].isEmpty()) {
          covenant.addBannedState(line1[3]);
        }
        for (Facility facility : bankFacilitiesMap.get(bankId)) {
          facility.getCovenant().update(covenant);
        }
      } else {
        int id = Integer.parseInt(line1[0]);
        Covenant covenant = facilities.get(id).getCovenant();
        if (!line1[1].isEmpty()) {
          covenant.setMaxDefaultLikelihood(new BigDecimal(line1[1]));
        }
        if (!line1[3].isEmpty()) {
          covenant.addBannedState(line1[3]);
        }
      }
    }
    covenantFileReader.close();
  }

  /**
   * Load loans CSV.
   *
   * @param classloader the classloader
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CsvValidationException the csv validation exception
   */
  private static void loadLoansCSV(ClassLoader classloader)
      throws FileNotFoundException, IOException, CsvValidationException {
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
  }
}
