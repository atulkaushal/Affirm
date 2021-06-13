package com.atulkaushal.github.affirm.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.atulkaushal.github.affirm.writer.YeildsWriter;

/** The Class YeildsService. */
public class YeildsService {

  /** The facilities expected yeild map. */
  Map<Integer, BigDecimal> facilitiesExpectedYeildMap = new HashMap<Integer, BigDecimal>();

  /**
   * Adds the yeilds info.
   *
   * @param facilityId the facility id
   * @param expectedYeild the expected yeild
   */
  public void addYeildsInfo(Integer facilityId, BigDecimal expectedYeild) {
    if (facilitiesExpectedYeildMap.containsKey(facilityId)) {
      BigDecimal value = facilitiesExpectedYeildMap.get(facilityId);
      value = value.add(expectedYeild);
      facilitiesExpectedYeildMap.put(facilityId, value);
    } else {
      facilitiesExpectedYeildMap.put(facilityId, expectedYeild);
    }
  }

  /**
   * Generate report.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void generateReport() throws IOException {
    YeildsWriter yeildsWriter = new YeildsWriter();
    yeildsWriter.generateYeildsFile(facilitiesExpectedYeildMap);
  }
}
