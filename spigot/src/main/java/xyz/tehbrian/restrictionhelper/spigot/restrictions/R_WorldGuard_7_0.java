package xyz.tehbrian.restrictionhelper.spigot.restrictions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;
import xyz.tehbrian.restrictionhelper.spigot.SpigotRestriction;

import java.util.Objects;

@RestrictionInfo(name = "WorldGuard", version = "7.0", mainClass = "com.sk89q.worldguard.bukkit.WorldGuardPlugin")
public final class R_WorldGuard_7_0 extends SpigotRestriction {

    public R_WorldGuard_7_0(final @NonNull Logger logger) {
        super(logger);
    }

    @Override
    public boolean check(final @NonNull Player bukkitPlayer, final @NonNull Location bukkitLoc, final ActionType actionType) {
        Objects.requireNonNull(bukkitPlayer);
        Objects.requireNonNull(bukkitLoc);

        final com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(bukkitLoc);
        final LocalPlayer player = WorldGuardPlugin.inst().wrapPlayer(bukkitPlayer);

        final WorldGuard worldGuard = WorldGuard.getInstance();

        if (worldGuard.getPlatform().getSessionManager().hasBypass(player, player.getWorld())) {
            // Player has permission to bypass
            this.logger.trace("WG: PASSED - Player has bypass.");
            return true;
        }

        // https://worldguard.enginehub.org/en/latest/developer/regions/protection-query/
        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionQuery query = container.createQuery();

        if (query.testState(loc, player, Flags.BUILD)) {
            // Player has permission for everything, no need to check specific flags
            this.logger.trace("WG: PASSED - Player has BUILD flag.");
            return true;
        }

        final StateFlag flagToCheck = switch (actionType) {
            case ALL -> Flags.BUILD;
            case PLACE -> Flags.BLOCK_PLACE;
            case BREAK -> Flags.BLOCK_BREAK;
            case INTERACT -> Flags.INTERACT;
        };

        if (!query.testState(loc, player, flagToCheck)) {
            this.logger.trace("WG: FAILED - Player does not have {} flag.", flagToCheck.getName());
            return false;
        }

        this.logger.trace("WG: PASSED - Default return value.");
        return true;
    }

}
