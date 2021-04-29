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
import java.util.ArrayList;
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
            final @NonNull List<Class<? extends BukkitRestriction>> possibleRestrictions) {
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
        List<String> pluginNames = new ArrayList<>();
        plugins.forEach(p -> pluginNames.add(p.getName()));

        List<String> possibleRestrictionNames = new ArrayList<>();
        possibleRestrictions.forEach(r -> possibleRestrictionNames.add(r.getSimpleName()));

        logger.info("Finding applicable restrictions for plugins {} from restrictions {}.",
                String.join(", ", pluginNames),
                String.join(", ", possibleRestrictionNames));

        for (Plugin plugin : plugins) {
            logger.debug("Beginning restriction-check loop for plugin {}.", plugin.getName());

            for (Class<? extends BukkitRestriction> restrictionClass : possibleRestrictions) {
                logger.debug("Checking restriction {} for plugin {}.", restrictionClass.getSimpleName(), plugin.getName());

                RestrictionInfo info = restrictionClass.getAnnotation(RestrictionInfo.class);
                if (info == null) {
                    logger.debug("Failed because the class was not annotated with RestrictionInfo.");
                    continue;
                }

                PluginDescriptionFile description = plugin.getDescription();
                if (!description.getName().equals(info.name())) {
                    logger.debug("Failed because the plugin's name did not match the RestrictionInfo's specified name.");
                    logger.debug("Expected: {} Actual: {}", info.name(), description.getName());
                    continue;
                }
                if (!description.getMain().equals(info.main())) {
                    logger.debug("Failed because the plugin's main class did not match the RestrictionInfo's specified main class.");
                    logger.debug("Expected: {} Actual: {}", info.main(), description.getMain());
                    continue;
                }
                if (!description.getVersion().startsWith(info.version())) { // TODO make this work good.
                    logger.debug("Failed because the plugin's version did not start with the RestrictionInfo's specified version.");
                    logger.debug("Expected: {} Actual: {}", info.version(), description.getVersion());
                    continue;
                }

                this.logger.info("Found applicable restriction {} for plugin {} version {}. Attempting to register the restriction..",
                        restrictionClass.getCanonicalName(),
                        description.getName(),
                        description.getVersion());

                Constructor<? extends BukkitRestriction> constructor;
                try {
                    constructor = restrictionClass.getConstructor(Logger.class);
                } catch (NoSuchMethodException e) {
                    this.logger.error("Failed to register the restriction because it didn't have the proper constructor!", e);
                    continue;
                }

                BukkitRestriction restriction;
                try {
                    restriction = constructor.newInstance(this.logger);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    this.logger.error("Failed to register the restriction because there was an error when trying to instantiate it!", e);
                    continue;
                }

                restrictionHelper.registerRestriction(restriction);

                this.logger.info("Registered the restriction for {} successfully!",
                        description.getName());
            }
        }
    }
}
