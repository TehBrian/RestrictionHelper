package xyz.tehbrian.restrictionhelper.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

/**
 * A handler for all restrictions enforced by a specific version of a specific
 * plugin.
 *
 * @param <P> the player type
 * @param <L> the location type
 */
public abstract class Restriction<P, L> {

  /**
   * Used to log whether a check fails or passes, and why.
   */
  protected final Logger logger;

  /**
   * @param logger the {@code Logger} used to log whether a check fails or
   *               passes, and why
   */
  public Restriction(final @NonNull Logger logger) {
    this.logger = logger;
  }

  /**
   * Checks whether {@code player} has sufficient permission to perform
   * {@code action} at {@code location}.
   *
   * @param player     the player
   * @param location   the location
   * @param actionType the {@code ActionType}
   * @return true if the player has permission, false if not
   */
  public abstract boolean check(@NonNull P player, @NonNull L location, ActionType actionType);

}
