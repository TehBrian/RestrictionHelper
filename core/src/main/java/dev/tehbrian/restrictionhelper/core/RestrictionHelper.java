package dev.tehbrian.restrictionhelper.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for RestrictionHelper. It mainly serves to hold the list of
 * {@link Restriction}s to be checked against.
 *
 * @param <P> the player type
 * @param <L> the location type
 * @param <R> the {@code Restriction} type
 */
public abstract class RestrictionHelper<P, L, R extends Restriction<P, L>> {

  /**
   * The internal list of {@code Restriction}s to be checked against.
   */
  private final List<R> restrictions = new ArrayList<>();

  /**
   * Registers a {@code Restriction} to be checked against.
   * <p>
   * NOTE: This <b>will not</b> affect the restrictions of other plugins.
   *
   * @param restriction the {@code Restriction} to register
   */
  public void registerRestriction(final @NonNull R restriction) {
    this.restrictions.add(restriction);
  }

  /**
   * Unregisters a {@code Restriction}.
   * <p>
   * NOTE: This <b>will not</b> affect the restrictions of other plugins.
   *
   * @param restriction the {@code Restriction} to unregister
   */
  public void unregisterRestriction(final @NonNull R restriction) {
    this.restrictions.remove(restriction);
  }

  /**
   * Gets the internal list of registered {@code Restrictions}.
   * <p>
   * NOTE: This list <b>is not</b> shared with other plugins.
   *
   * @return the list of registered {@code Restrictions}
   */
  public List<R> getRegisteredRestrictions() {
    return this.restrictions;
  }

  /**
   * Checks whether {@code player} has sufficient permission to perform
   * {@code action} at {@code location}, according to all registered restrictions.
   *
   * @param player     the player
   * @param location   the location
   * @param actionType the action
   * @return true if the player has permission, false if not
   */
  public boolean checkRestrictions(final @NonNull P player, final @NonNull L location, final ActionType actionType) {
    // iterate through all registered restrictions.
    // if any restriction returns false, return false immediately.
    // if all restrictions return true, return true.
    for (final R restriction : this.restrictions) {
      if (!restriction.check(player, location, actionType)) {
        return false;
      }
    }
    return true;
  }

}
