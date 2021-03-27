package xyz.tehbrian.restrictionhelper.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import xyz.tehbrian.restrictionhelper.core.Restriction;
import xyz.tehbrian.restrictionhelper.core.RestrictionHelper;
import xyz.tehbrian.restrictionhelper.core.RestrictionInfo;
import xyz.tehbrian.restrictionhelper.core.RestrictionLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * A utility class which registers {@link Restriction}s into a
 * {@link RestrictionHelper} instance according to {@link #plugins}.
 */
public class BukkitRestrictionLoader extends RestrictionLoader<Player, Location, BukkitRestriction, BukkitRestrictionHelper, Plugin> {

    /**
     * @param logger               the {@code Logger} used to log whether a check fails or
     *                             passes, and why
     * @param plugins              the plugins to check
     * @param possibleRestrictions the {@code Restriction}s to maybe be registered
     */
    public BukkitRestrictionLoader(
            final @NonNull Logger logger,
            final @NonNull List<Plugin> plugins,
            final @NonNull List<Class<BukkitRestriction>> possibleRestrictions) {
        super(logger, plugins, possibleRestrictions);
    }

    /**
     * For each plugin in {@link #plugins}, checks whether any of the
     * {@link #possibleRestrictions} were made for that specific version of
     * that specific plugin and, if so, constructs and registers the
     * {@code Restriction} into {@code RestrictionHelper}.
     *
     * @param restrictionHelper the {@code RestrictionHelper} instance
     */
    public void load(final @NonNull BukkitRestrictionHelper restrictionHelper) {
        for (Plugin plugin : plugins) {
            for (Class<BukkitRestriction> restrictionClass : possibleRestrictions) {
                RestrictionInfo info = restrictionClass.getAnnotation(RestrictionInfo.class);
                if (info == null) {
                    return;
                }

                PluginDescriptionFile description = plugin.getDescription();
                if (description.getName().equals(info.name())) {
                    return;
                }
                if (!description.getMain().equals(info.main())) {
                    return;
                }
                if (!description.getVersion().startsWith(info.version())) { // TODO make this work good.
                    return;
                }

                this.logger.info("Found applicable Restriction {} for plugin {} version {}. Attempting to register the Restriction..",
                        restrictionClass.getPackageName(),
                        description.getName(),
                        description.getVersion());

                Constructor<BukkitRestriction> constructor;
                try {
                    constructor = restrictionClass.getConstructor(Logger.class);
                } catch (NoSuchMethodException e) {
                    this.logger.error("Failed to register the Restriction because it didn't have the proper constructor!", e);
                    return;
                }

                BukkitRestriction restriction;
                try {
                    restriction = constructor.newInstance(this.logger);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    this.logger.error("Failed to register the Restriction because there was an error when trying to instantiate it!", e);
                    return;
                }

                restrictionHelper.registerRestriction(restriction);
                this.logger.info("The Restriction for {} was registered successfully!",
                        description.getName());
            }
        }
    }
}
