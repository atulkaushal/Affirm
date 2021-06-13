package com.atulkaushal.github.affirm.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Covenant.
 *
 * @author Atul
 */
public class Covenant {

  /** The max default likelihood. */
  private BigDecimal maxDefaultLikelihood;

  /** The banned states. */
  private Set<String> bannedStates = new HashSet<String>();

  /**
   * Gets the banned states.
   *
   * @return the banned states
   */
  public Set<String> getBannedStates() {
    return bannedStates;
  }

  /**
   * Adds the banned state.
   *
   * @param state the state
   */
  public void addBannedState(String state) {
    bannedStates.add(state);
  }

  /**
   * Gets the max default likelihood.
   *
   * @return the max default likelihood
   */
  public BigDecimal getMaxDefaultLikelihood() {
    return maxDefaultLikelihood;
  }

  /**
   * Sets the max default likelihood.
   *
   * @param maxDefaultLikelihood the new max default likelihood
   */
  public void setMaxDefaultLikelihood(BigDecimal maxDefaultLikelihood) {
    this.maxDefaultLikelihood = maxDefaultLikelihood;
  }

  /**
   * Checks if is allowed.
   *
   * @param defaultLikelihood the default likelihood
   * @param state the state
   * @return true, if is allowed
   */
  public boolean isAllowed(BigDecimal defaultLikelihood, String state) {
    return maxDefaultLikelihood.compareTo(defaultLikelihood) >= 0 && !bannedStates.contains(state);
  }

  /**
   * Update.
   *
   * @param covenant the covenant
   */
  public void update(Covenant covenant) {
    if (covenant.getMaxDefaultLikelihood() != null) {
      this.setMaxDefaultLikelihood(covenant.getMaxDefaultLikelihood());
    }
    for (String state : covenant.getBannedStates()) {
      this.addBannedState(state);
    }
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Covenant [maxDefaultLikelihood="
        + maxDefaultLikelihood
        + ", bannedStates="
        + bannedStates
        + "]";
  }
}
