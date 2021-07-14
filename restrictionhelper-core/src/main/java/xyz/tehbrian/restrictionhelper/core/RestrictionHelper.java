package xyz.tehbrian.restrictionhelper.core;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class which holds the list of {@code Restriction}s to be checked
 * against.
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
     * NOTE: Since RestrictionHelper is a shade-in dependency, other plugins
     * <b>will not</b> be able to check against restrictions that your plugin
     * registers.
     *
     * @param restriction the {@code Restriction} to register
     */
    public void registerRestriction(final @NonNull R restriction) {
        this.restrictions.add(restriction);
    }

    /**
     * Unregisters a {@code Restriction}.
     * <p>
     * NOTE: Since RestrictionHelper is a shade-in dependency, other plugins
     * <b>will not</b> be affected by the restrictions that your plugin
     * unregisters.
     *
     * @param restriction the {@code Restriction} to unregister
     */
    public void unregisterRestriction(final @NonNull R restriction) {
        this.restrictions.remove(restriction);
    }

    /**
     * Gets the internal list of registered {@code Restrictions}.
     * <p>
     * NOTE: Since RestrictionHelper is a shade-in dependency, this list
     * <b>is not</b> shared with other plugins.
     *
     * @return the list of registered {@code Restrictions}
     */
    public List<R> getRegisteredRestrictions() {
        return this.restrictions;
    }

    /**
     * Checks whether {@code player} has sufficient permission to perform
     * {@code action} at {@code location}. according to all registered restrictions.
     *
     * @param player     the player
     * @param location   the location
     * @param actionType the ActionType
     * @return true if the player has permission, false if not
     */
    public boolean checkRestrictions(final @NonNull P player, final @NonNull L location, final ActionType actionType) {
        // Iterate through all registered restrictions.
        // If any restriction returns false, return false immediately.
        // If all restrictions return true, return true.
        for (R restriction : this.restrictions) {
            if (!restriction.check(player, location, actionType)) {
                return false;
            }
        }
        return true;
    }

}
