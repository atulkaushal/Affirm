package com.atulkaushal.github.affirm.model;

import java.math.BigDecimal;

/**
 * The Class Loan.
 *
 * @author Atul
 */
public class Loan {

  /** The id. */
  private Integer id;

  /** The amount. */
  private BigDecimal amount;

  /** The interest rate. */
  private BigDecimal interestRate;

  /** The default likelihood. */
  private BigDecimal defaultLikelihood;

  /** The state. */
  private String state;

  /**
   * Instantiates a new loan.
   *
   * @param id the id
   * @param amount the amount
   * @param interestRate the interest rate
   * @param defaultLikelihood the default likelihood
   * @param state the state
   */
  public Loan(
      Integer id,
      BigDecimal amount,
      BigDecimal interestRate,
      BigDecimal defaultLikelihood,
      String state) {
    super();
    this.id = id;
    this.amount = amount;
    this.interestRate = interestRate;
    this.defaultLikelihood = defaultLikelihood;
    this.state = state;
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
   * Gets the default likelihood.
   *
   * @return the default likelihood
   */
  public BigDecimal getDefaultLikelihood() {
    return defaultLikelihood;
  }

  /**
   * Sets the default likelihood.
   *
   * @param defaultLikelihood the new default likelihood
   */
  public void setDefaultLikelihood(BigDecimal defaultLikelihood) {
    this.defaultLikelihood = defaultLikelihood;
  }

  /**
   * Gets the state.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Loan [id="
        + id
        + ", amount="
        + amount
        + ", interestRate="
        + interestRate
        + ", defaultLikelihood="
        + defaultLikelihood
        + ", state="
        + state
        + "]";
  }
}
