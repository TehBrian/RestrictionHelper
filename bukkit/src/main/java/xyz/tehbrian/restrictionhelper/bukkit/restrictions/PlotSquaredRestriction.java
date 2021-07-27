package xyz.tehbrian.restrictionhelper.bukkit.restrictions;

import com.plotsquared.core.plot.Plot;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import xyz.tehbrian.restrictionhelper.bukkit.BukkitRestriction;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;

import java.util.Objects;

@RestrictionInfo(name = "PlotSquared", version = "5.13", main = "com.plotsquared.bukkit.BukkitMain")
public final class PlotSquaredRestriction extends BukkitRestriction {

    public PlotSquaredRestriction(final @NonNull Logger logger) {
        super(logger);
    }

    @Override
    public boolean check(final @NonNull Player player, final @NonNull Location bukkitLoc, final ActionType actionType) {
        final com.plotsquared.core.location.Location psLoc = new com.plotsquared.core.location.Location(
                Objects.requireNonNull(bukkitLoc.getWorld()).getName(),
                bukkitLoc.getBlockX(),
                bukkitLoc.getBlockY(),
                bukkitLoc.getBlockZ()
        );

        if (psLoc.isPlotArea() || psLoc.isPlotRoad()) {
            // Location is in a plot area.

            // TODO If the player isn't in a proper plot (ie. the road) then plot will be null.
            // We need to figure out another method
            // of checking whether a player can build,
            // so that players who have proper permissions
            // will still be validated in roads.
            final Plot plot = psLoc.getPlot();

            if (plot == null) {
                this.logger.trace("PS: FAILED - Plot is null.");
                return false;
            }

            if (plot.isAdded(player.getUniqueId())) {
                this.logger.trace("PS: PASSED - Player is added to plot.");
                return true;
            } else {
                this.logger.trace("PS: FAILED - Player is not added to plot.");
                return false;
            }
        } else {
            // Location is not in a plot area.
            this.logger.trace("PS: PASSED - Location isn't PlotArea or PlotRoad.");
            return true;
        }
    }

}
