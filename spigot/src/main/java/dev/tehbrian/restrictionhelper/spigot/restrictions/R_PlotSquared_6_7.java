package dev.tehbrian.restrictionhelper.spigot.restrictions;

import com.plotsquared.bukkit.player.BukkitPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.permissions.Permission;
import com.plotsquared.core.plot.Plot;
import dev.tehbrian.restrictionhelper.core.ActionType;
import dev.tehbrian.restrictionhelper.core.RestrictionInfo;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestriction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Objects;

@RestrictionInfo(name = "PlotSquared", version = "^(6|7).*", mainClass = "com.plotsquared.bukkit.BukkitPlatform")
@SuppressWarnings("checkstyle:TypeName")
public final class R_PlotSquared_6_7 extends SpigotRestriction {

	/**
	 * @param logger the logger used to log whether a check fails or passes,
	 *               and why
	 */
	public R_PlotSquared_6_7(final Logger logger) {
		super(logger);
	}

	@Override
	public boolean check(
			final Player bukkitPlayer,
			final Location bukkitLoc,
			final ActionType actionType
	) {
		Objects.requireNonNull(bukkitPlayer);
		Objects.requireNonNull(bukkitLoc);

		final com.plotsquared.core.location.Location loc = BukkitUtil.adapt(bukkitLoc);
		final BukkitPlayer player = BukkitUtil.adapt(bukkitPlayer);

		if (loc.isPlotRoad()) {
			final boolean passed = switch (actionType) {
				case ALL -> (player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_ROAD.toString())
						&& player.hasPermission(Permission.PERMISSION_ADMIN_BUILD_ROAD.toString())
						&& player.hasPermission(Permission.PERMISSION_ADMIN_INTERACT_ROAD.toString())
						&& player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_VEHICLE_ROAD.toString()));
				case BREAK -> player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_ROAD.toString());
				case PLACE -> player.hasPermission(Permission.PERMISSION_ADMIN_BUILD_ROAD.toString());
				case INTERACT -> player.hasPermission(Permission.PERMISSION_ADMIN_INTERACT_ROAD.toString());
			};
			if (passed) {
				this.logger.debug("PS: PASSED - Checked player permissions. Use LuckPerms verbose to see which one.");
			} else {
				this.logger.debug("PS: FAILED - Checked player permissions. Use LuckPerms verbose to see which one.");
			}
			return passed;
		} else if (loc.isPlotArea()) {
			final Plot plot = loc.getPlot();

			if (plot == null) {
				this.logger.debug("PS: FAILED - Plot is null.");
				return false;
			}

			if (plot.isAdded(player.getUUID())) {
				this.logger.debug("PS: PASSED - Player is added to plot.");
				return true;
			} else {
				final boolean override = switch (actionType) {
					case ALL -> (player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_UNOWNED.toString())
							&& player.hasPermission(Permission.PERMISSION_ADMIN_BUILD_UNOWNED.toString())
							&& player.hasPermission(Permission.PERMISSION_ADMIN_INTERACT_UNOWNED.toString())
							&& player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_VEHICLE_UNOWNED.toString()));
					case BREAK -> player.hasPermission(Permission.PERMISSION_ADMIN_DESTROY_UNOWNED.toString());
					case PLACE -> player.hasPermission(Permission.PERMISSION_ADMIN_BUILD_UNOWNED.toString());
					case INTERACT -> player.hasPermission(Permission.PERMISSION_ADMIN_INTERACT_UNOWNED.toString());
				};
				if (override) {
					this.logger.debug("PS: PASSED - Player is not added to plot but has override permission.");
					return true;
				} else {
					this.logger.debug("PS: FAILED - Player is not added to plot and does not have override permission.");
					return false;
				}
			}
		} else {
			this.logger.debug("PS: PASSED - Location isn't PlotArea or PlotRoad.");
			return true;
		}
	}

}

