package com.atulkaushal.github.affirm.model;

import java.math.BigDecimal;

/**
 * The Class Facility.
 *
 * @author Atul
 */
public class Facility {

  /** The id. */
  private Integer id;

  /** The bank id. */
  private Integer bankId;

  /** The interest rate. */
  private BigDecimal interestRate;

  /** The amount. */
  private BigDecimal amount;

  /** The covenant. */
  private Covenant covenant;

  /**
   * Instantiates a new facility.
   *
   * @param bankId the bank id
   * @param id the id
   * @param interestRate the interest rate
   * @param amount the amount
   * @param covenant the covenant
   */
  public Facility(
      Integer bankId, Integer id, BigDecimal interestRate, BigDecimal amount, Covenant covenant) {
    super();
    this.bankId = bankId;
    this.id = id;
    this.interestRate = interestRate;
    this.amount = amount;
    this.covenant = covenant;
  }

  /**
   * Gets the bank id.
   *
   * @return the bank id
   */
  public Integer getBankId() {
    return bankId;
  }

  /**
   * Sets the bank id.
   *
   * @param bankId the new bank id
   */
  public void setBankId(Integer bankId) {
    this.bankId = bankId;
  }

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
   * Gets the interest rate.
   *
   * @return the interest rate
   */
  public BigDecimal getInterestRate() {
    return interestRate;
  }

  /**
   * Sets the interest rate.
   *
   * @param interestRate the new interest rate
   */
  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  /**
   * Gets the amount.
   *
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Sets the amount.
   *
   * @param amount the new amount
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * Gets the covenant.
   *
   * @return the covenant
   */
  public Covenant getCovenant() {
    return covenant;
  }

  /**
   * Sets the covenant.
   *
   * @param covenant the new covenant
   */
  public void setCovenant(Covenant covenant) {
    this.covenant = covenant;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Facility [id="
        + id
        + ", bankId="
        + bankId
        + ", interestRate="
        + interestRate
        + ", amount="
        + amount
        + ", covenant="
        + covenant
        + "]";
  }
}
