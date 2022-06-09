package xyz.tehbrian.restrictionhelper.spigot;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import xyz.tehbrian.restrictionhelper.core.Restriction;

/**
 * A handler for all restrictions enforced by a specific version of a specific
 * plugin.
 */
public abstract class SpigotRestriction extends Restriction<Player, Location> {

    /**
     * @param logger the logger used to log whether a check fails or passes,
     *               and why
     */
    public SpigotRestriction(final @NonNull Logger logger) {
        super(logger);
    }

}
