package dev.tehbrian.restrictionhelper.spigot;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import dev.tehbrian.restrictionhelper.core.Restriction;
import dev.tehbrian.restrictionhelper.core.RestrictionHelper;
import dev.tehbrian.restrictionhelper.core.RestrictionInfo;
import dev.tehbrian.restrictionhelper.core.RestrictionLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class which registers {@link Restriction}s into a
 * {@link RestrictionHelper} instance according to a list of plugins.
 */
public class SpigotRestrictionLoader
    extends RestrictionLoader<Player, Location, SpigotRestriction, SpigotRestrictionHelper, Plugin> {

  /**
   * @param logger               the {@code Logger} used to log whether a check fails or
   *                             passes, and why
   * @param plugins              the plugins to check
   * @param possibleRestrictions the {@code Restriction}s to maybe be registered
   */
  public SpigotRestrictionLoader(
      final @NonNull Logger logger,
      final @NonNull List<Plugin> plugins,
      final @NonNull List<Class<? extends SpigotRestriction>> possibleRestrictions
  ) {
    super(logger, plugins, possibleRestrictions);
  }

  /**
   * For each plugin in {@link #plugins}, checks whether any of the
   * {@link #possibleRestrictions} were made for that specific version of
   * that specific plugin and, if so, constructs the {@code Restriction} and
   * registers it into {@code RestrictionHelper}.
   *
   * @param restrictionHelper the {@code RestrictionHelper} instance
   */
  @Override
  public void load(final @NonNull SpigotRestrictionHelper restrictionHelper) {
    final List<String> pluginNames = new ArrayList<>();
    plugins.forEach(p -> pluginNames.add(p.getName()));

    final List<String> possibleRestrictionNames = new ArrayList<>();
    possibleRestrictions.forEach(r -> possibleRestrictionNames.add(r.getSimpleName()));

    logger.info(
        "Finding applicable restrictions for plugins [{}] from restrictions [{}].",
        String.join(", ", pluginNames),
        String.join(", ", possibleRestrictionNames)
    );

    for (final Plugin plugin : plugins) {
      logger.debug("Beginning restriction-check loop for plugin {}.", plugin.getName());

      for (final Class<? extends SpigotRestriction> restrictionClass : possibleRestrictions) {
        logger.debug("Checking restriction {} for plugin {}.", restrictionClass.getSimpleName(), plugin.getName());

        final RestrictionInfo info = restrictionClass.getAnnotation(RestrictionInfo.class);
        if (info == null) {
          logger.debug("Failed because the class was not annotated with RestrictionInfo.");
          continue;
        }

        final PluginDescriptionFile description = plugin.getDescription();
        if (!description.getName().equals(info.name())) {
          logger.debug("Failed because the plugin's name did not match the RestrictionInfo's specified name.");
          logger.debug("Expected: {} Actual: {}", info.name(), description.getName());
          continue;
        }
        if (!description.getMain().equals(info.mainClass())) {
          logger.debug("Failed because the plugin's main class"
              + " did not match the RestrictionInfo's specified main class.");
          logger.debug("Expected: {} Actual: {}", info.mainClass(), description.getMain());
          continue;
        }
        if (!description.getVersion().startsWith(info.version())) { // TODO: better version checking system?
          logger.debug("Failed because the plugin's version did not start with the RestrictionInfo's specified version.");
          logger.debug("Expected: {} Actual: {}", info.version(), description.getVersion());
          continue;
        }

        this.logger.info(
            "Found applicable restriction {} for plugin {} version {}. Attempting to register the restriction.",
            restrictionClass.getCanonicalName(),
            description.getName(),
            description.getVersion()
        );

        final Constructor<? extends SpigotRestriction> constructor;
        try {
          constructor = restrictionClass.getConstructor(Logger.class);
        } catch (final NoSuchMethodException e) {
          this.logger.error("Failed to register the restriction because it lacked the proper constructor.", e);
          continue;
        }

        final SpigotRestriction restriction;
        try {
          restriction = constructor.newInstance(this.logger);
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
          this.logger.error("Failed to register the restriction because its constructor threw an error.", e);
          continue;
        }

        restrictionHelper.registerRestriction(restriction);

        this.logger.info("Registered the restriction for {} successfully.", description.getName());
      }
    }
  }

}
