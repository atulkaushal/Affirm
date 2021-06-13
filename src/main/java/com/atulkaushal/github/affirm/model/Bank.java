package com.atulkaushal.github.affirm.model;

/**
 * The Class Bank.
 *
 * @author Atul
 */
public class Bank {

  /** The id. */
  private Integer id;

  /** The bank name. */
  private String bankName;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the bank name.
   *
   * @return the bank name
   */
  public String getBankName() {
    return bankName;
  }

  /**
   * Sets the bank name.
   *
   * @param bankName the new bank name
   */
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Bank [id=" + id + ", bankName=" + bankName + "]";
  }
}
