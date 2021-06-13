package com.atulkaushal.github.affirm.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

/**
 * The Class YeildsWriter.
 *
 * @author Atul
 */
public class YeildsWriter {
  /** The assignment file name. */
  final String YEILDS_FILE_NAME = "yeilds.csv";

  /** The file headers. */
  final String[] FILE_HEADERS = {"facility_id, expected_yeild"};

  /**
   * Generate yeilds file.
   *
   * @param facilitiesExpectedYeildMap the facilities expected yeild map
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void generateYeildsFile(Map<Integer, BigDecimal> facilitiesExpectedYeildMap)
      throws IOException {

    CSVWriter writer =
        new CSVWriter(
            new FileWriter(YEILDS_FILE_NAME),
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.NO_ESCAPE_CHARACTER,
            System.lineSeparator());
    writer.writeNext(FILE_HEADERS);
    for (Iterator<Entry<Integer, BigDecimal>> iterator =
            facilitiesExpectedYeildMap.entrySet().iterator();
        iterator.hasNext(); ) {
      Entry<Integer, BigDecimal> entry = iterator.next();
      // Round off the value to nearest number.
      String[] line = {
        entry.getKey().toString(), entry.getValue().setScale(0, RoundingMode.HALF_UP).toString()
      };
      writer.writeNext(line);
    }
    writer.close();
  }
}
