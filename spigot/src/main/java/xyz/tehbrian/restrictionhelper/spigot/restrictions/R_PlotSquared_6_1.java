package xyz.tehbrian.restrictionhelper.spigot.restrictions;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.permissions.Permission;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.util.Permissions;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestriction;

import java.util.Objects;

@RestrictionInfo(name = "PlotSquared", version = "6.1", mainClass = "com.plotsquared.bukkit.BukkitPlatform")
@SuppressWarnings("checkstyle:TypeName")
public final class R_PlotSquared_6_1 extends SpigotRestriction {

    public R_PlotSquared_6_1(final @NonNull Logger logger) {
        super(logger);
    }

    @Override
    public boolean check(final @NonNull Player bukkitPlayer, final @NonNull Location bukkitLoc, final ActionType actionType) {
        Objects.requireNonNull(bukkitPlayer);
        Objects.requireNonNull(bukkitLoc);

        final com.plotsquared.core.location.Location loc = BukkitUtil.adapt(bukkitLoc);
        final BukkitPlayer player = BukkitUtil.adapt(bukkitPlayer);

        if (loc.isPlotRoad()) {
            final boolean passed = switch (actionType) {
                case ALL -> (Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_ROAD)
                        && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_BUILD_ROAD)
                        && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_INTERACT_ROAD)
                        && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_VEHICLE_ROAD));
                case BREAK -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_ROAD);
                case PLACE -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_BUILD_ROAD);
                case INTERACT -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_INTERACT_ROAD);
            };
            if (passed) {
                this.logger.trace("PS: PASSED - Checked player permissions. Use LuckPerms verbose to see which one.");
            } else {
                this.logger.trace("PS: FAILED - Checked player permissions. Use LuckPerms verbose to see which one.");
            }
            return passed;
        } else if (loc.isPlotArea()) {
            final Plot plot = loc.getPlot();

            if (plot == null) {
                this.logger.trace("PS: FAILED - Plot is null.");
                return false;
            }

            if (plot.isAdded(player.getUUID())) {
                this.logger.trace("PS: PASSED - Player is added to plot.");
                return true;
            } else {
                final boolean override = switch (actionType) {
                    case ALL -> (Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_UNOWNED)
                            && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_BUILD_UNOWNED)
                            && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_INTERACT_UNOWNED)
                            && Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_VEHICLE_UNOWNED));
                    case BREAK -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_DESTROY_UNOWNED);
                    case PLACE -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_BUILD_UNOWNED);
                    case INTERACT -> Permissions.hasPermission(player, Permission.PERMISSION_ADMIN_INTERACT_UNOWNED);
                };
                if (override) {
                    this.logger.trace("PS: PASSED - Player is not added to plot but has override permission.");
                    return true;
                } else {
                    this.logger.trace("PS: FAILED - Player is not added to plot and does not have override permission.");
                    return false;
                }
            }
        } else {
            this.logger.trace("PS: PASSED - Location isn't PlotArea or PlotRoad.");
            return true;
        }
    }

}

