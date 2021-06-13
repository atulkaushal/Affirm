package com.atulkaushal.github.affirm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.atulkaushal.github.affirm.model.Covenant;
import com.atulkaushal.github.affirm.model.Facility;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

/** The Class FacilityService. */
public class FacilityService {

  /** The facilities. */
  final Map<Integer, Facility> facilities = new HashMap<Integer, Facility>();

  /** The facilities by interest rate. */
  SortedMap<BigDecimal, List<Facility>> facilitiesByInterestRate =
      new TreeMap<BigDecimal, List<Facility>>();

  /** The bank level covenant. */
  Map<Integer, Covenant> bankLevelCovenant = new HashMap<>();

  /** The bank facilities map. */
  Map<Integer, List<Facility>> bankFacilitiesMap = new HashMap<>();

  /**
   * Instantiates a new facility service.
   *
   * @param facilityfileName the facilityfile name
   * @param covenantFileName the covenant file name
   * @throws CsvValidationException the csv validation exception
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public FacilityService(String facilityfileName, String covenantFileName)
      throws CsvValidationException, FileNotFoundException, IOException {
    loadFacilityCSV(facilityfileName);
    loadCovenantCSV(covenantFileName);
  }

  /**
   * Gets the facilities sorted by interest rate.
   *
   * @return the facilities sorted by interest rate
   */
  SortedMap<BigDecimal, List<Facility>> getFacilitiesSortedByInterestRate() {
    return facilitiesByInterestRate;
  }

  /**
   * Load facility CSV.
   *
   * @param facilityFileName the facility file name
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CsvValidationException the csv validation exception
   */
  void loadFacilityCSV(String facilityFileName)
      throws FileNotFoundException, IOException, CsvValidationException {

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File facilityFile = new File(classloader.getResource(facilityFileName).getFile());
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
   * @param covenantFileName the covenant file name
   * @throws FileNotFoundException the file not found exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws CsvValidationException the csv validation exception
   */
  void loadCovenantCSV(String covenantFileName)
      throws FileNotFoundException, IOException, CsvValidationException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File covenantFile = new File(classloader.getResource(covenantFileName).getFile());
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
}
