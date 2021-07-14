package xyz.tehbrian.restrictionhelper.bukkit.restrictions;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import xyz.tehbrian.restrictionhelper.bukkit.BukkitRestriction;
import xyz.tehbrian.restrictionhelper.core.ActionType;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;

import java.util.Objects;

@RestrictionInfo(name = "WorldGuard", version = "7.0.4", main = "com.sk89q.worldguard.bukkit.WorldGuardPlugin")
public final class WorldGuardRestriction extends BukkitRestriction {

    public WorldGuardRestriction(final @NonNull Logger logger) {
        super(logger);
    }

    @Override
    public boolean check(final @NonNull Player player, final @NonNull Location bukkitLoc, final ActionType actionType) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(bukkitLoc);

        // Convert Bukkit objects to WorldEdit's proprietary objects
        com.sk89q.worldedit.util.Location weLoc = BukkitAdapter.adapt(bukkitLoc);
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        // WorldGuard API
        WorldGuard worldGuard = WorldGuard.getInstance();

        // Player has permission to bypass
        if (worldGuard.getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld())) {
            this.logger.trace("WG: PASSED - Player has bypass.");
            return true;
        }

        // Shamelessly copied from https://worldguard.enginehub.org/en/latest/developer/regions/protection-query/
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        // Player has permission for everything, no need to check
        // specific flags.
        if (query.testState(weLoc, localPlayer, Flags.BUILD)) {
            this.logger.trace("WG: PASSED - Player has BUILD flag.");
            return true;
        }

        StateFlag flagToCheck = switch (actionType) {
            case ALL -> Flags.BUILD;
            case PLACE -> Flags.BLOCK_PLACE;
            case BREAK -> Flags.BLOCK_BREAK;
            case INTERACT -> Flags.INTERACT;
        };

        if (!query.testState(weLoc, localPlayer, flagToCheck)) {
            this.logger.trace("WG: FAILED - Player does not have {} flag.", flagToCheck.getName());
            return false;
        }

        this.logger.trace("WG: PASSED - Default return value.");
        return true;
    }

}
