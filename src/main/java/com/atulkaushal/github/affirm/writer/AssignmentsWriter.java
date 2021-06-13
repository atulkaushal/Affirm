package com.atulkaushal.github.affirm.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

/**
 * The Class AssignmentsWriter.
 *
 * @author Atul
 */
public class AssignmentsWriter {

  /** The assignment file name. */
  final String ASSIGNMENT_FILE_NAME = "assignment.csv";

  /** The file headers. */
  final String[] FILE_HEADERS = {"loan_id, facility_id"};

  /**
   * Generate assignment file.
   *
   * @param loanAssignmentMap the loan assignment map
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void generateAssignmentFile(Map<Integer, Integer> loanAssignmentMap) throws IOException {

    CSVWriter writer =
        new CSVWriter(
            new FileWriter(ASSIGNMENT_FILE_NAME),
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.NO_QUOTE_CHARACTER,
            CSVWriter.NO_ESCAPE_CHARACTER,
            System.lineSeparator());
    writer.writeNext(FILE_HEADERS);
    for (Iterator<Entry<Integer, Integer>> iterator = loanAssignmentMap.entrySet().iterator();
        iterator.hasNext(); ) {
      Entry<Integer, Integer> entry = iterator.next();
      String[] line = {
        entry.getKey().toString(), entry.getValue().equals(0) ? "" : entry.getValue().toString()
      };
      writer.writeNext(line);
    }
    writer.close();
  }
}
