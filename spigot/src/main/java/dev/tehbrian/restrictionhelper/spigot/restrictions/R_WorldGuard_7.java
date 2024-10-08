package dev.tehbrian.restrictionhelper.spigot.restrictions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.tehbrian.restrictionhelper.core.ActionType;
import dev.tehbrian.restrictionhelper.core.RestrictionInfo;
import dev.tehbrian.restrictionhelper.spigot.SpigotRestriction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Objects;

@RestrictionInfo(name = "WorldGuard", version = "^7.*", mainClass = "com.sk89q.worldguard.bukkit.WorldGuardPlugin")
@SuppressWarnings("checkstyle:TypeName")
public final class R_WorldGuard_7 extends SpigotRestriction {

	/**
	 * @param logger the logger used to log whether a check fails or passes,
	 *               and why
	 */
	public R_WorldGuard_7(final Logger logger) {
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

		final com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(bukkitLoc);
		final LocalPlayer player = WorldGuardPlugin.inst().wrapPlayer(bukkitPlayer);

		final WorldGuard worldGuard = WorldGuard.getInstance();

		if (worldGuard.getPlatform().getSessionManager().hasBypass(player, player.getWorld())) {
			// player has permission to bypass
			this.logger.debug("WG: PASSED - Player has bypass.");
			return true;
		}

		// https://worldguard.enginehub.org/en/latest/developer/regions/protection-query/
		final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		final RegionQuery query = container.createQuery();

		if (query.testState(loc, player, Flags.BUILD)) {
			// player has permission for everything, no need to check specific flags
			this.logger.debug("WG: PASSED - Player has BUILD flag.");
			return true;
		}

		final StateFlag flagToCheck = switch (actionType) {
			case ALL -> Flags.BUILD;
			case PLACE -> Flags.BLOCK_PLACE;
			case BREAK -> Flags.BLOCK_BREAK;
			case INTERACT -> Flags.INTERACT;
		};

		if (!query.testState(loc, player, flagToCheck)) {
			this.logger.debug("WG: FAILED - Player does not have {} flag.", flagToCheck.getName());
			return false;
		}

		this.logger.debug("WG: PASSED - Default return value.");
		return true;
	}

}
