package xyz.tehbrian.restrictionhelper.spigot.restrictions;

import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestriction;

import java.util.Objects;

@RestrictionInfo(name = "PlotSquared", version = "6.1", mainClass = "com.plotsquared.bukkit.BukkitPlatform")
public final class R_PlotSquared_6_1 extends SpigotRestriction {

    public R_PlotSquared_6_1(final @NonNull Logger logger) {
        super(logger);
    }

    @Override
    public boolean check(final @NonNull Player player, final @NonNull Location bukkitLoc, final ActionType actionType) {
        final com.plotsquared.core.location.Location psLoc = com.plotsquared.core.location.Location.at(
                Objects.requireNonNull(bukkitLoc.getWorld()).getName(),
                BlockVector3.at(
                        bukkitLoc.getBlockX(),
                        bukkitLoc.getBlockY(),
                        bukkitLoc.getBlockZ()
                ),
                bukkitLoc.getYaw(),
                bukkitLoc.getPitch()
        );

        if (psLoc.isPlotArea() || psLoc.isPlotRoad()) {
            // Location is in a plot area.

            // FIXME: if the player isn't in a proper plot (ie. the road) then plot will be null
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
