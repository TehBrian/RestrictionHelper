package xyz.tehbrian.restrictionhelper;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The abstract class which all restrictions must extend.
 */
public abstract class Restriction {

    /**
     * The {@link DebugLogger} which will be used to log whether a check fails or passes, and why.
     */
    protected final DebugLogger debugLogger;

    /**
     * Private no-args constructor to enforce providing a {@link DebugLogger}.
     */
    private Restriction() {
        throw new IllegalArgumentException("No DebugLogger provided.");
    }

    /**
     * Creates an instance of {@link Restriction}.
     *
     * @param debugLogger the debug logger to be used
     */
    public Restriction(final DebugLogger debugLogger) {
        if (debugLogger == null) {
            throw new NullArgumentException("DebugLogger provided is null.");
        }

        this.debugLogger = debugLogger;
    }

    /**
     * Checks whether {@code player} has sufficient permission to perform
     * {@code action} at {@code loc}.
     *
     * @param player     the player
     * @param loc        the location to check
     * @param actionType the type of action
     * @return true if the player has permission, false if not
     */
    public abstract boolean check(Player player, Location loc, ActionType actionType);
}
