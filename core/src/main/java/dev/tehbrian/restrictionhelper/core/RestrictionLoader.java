package dev.tehbrian.restrictionhelper.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.List;

/**
 * A utility class which registers {@link Restriction}s into a
 * {@link RestrictionHelper} instance according to a list of plugins.
 *
 * @param <PLAYER> the player type
 * @param <L>      the location type
 * @param <R>      the {@code Restriction} type
 * @param <H>      the {@code RestrictionHelper} type
 * @param <PLUGIN> the plugin type
 */
public abstract class RestrictionLoader<PLAYER, L, R extends Restriction<PLAYER, L>, H extends RestrictionHelper<PLAYER, L, R>, PLUGIN> {

  /**
   * Used to log whether a check fails or passes, and why.
   */
  protected final Logger logger;
  /**
   * The plugins to check whether a certain {@code Restriction} from
   * {@link #possibleRestrictions} should be registered.
   */
  protected final List<PLUGIN> plugins;
  /**
   * The {@code Restriction}s to maybe be registered.
   */
  protected final List<Class<? extends R>> possibleRestrictions;

  /**
   * @param logger               the {@code Logger} used to log whether a check fails or
   *                             passes, and why
   * @param plugins              the plugins to check
   * @param possibleRestrictions the {@code Restriction}s to maybe be registered
   */
  public RestrictionLoader(
      final @NonNull Logger logger,
      final @NonNull List<PLUGIN> plugins,
      final @NonNull List<Class<? extends R>> possibleRestrictions
  ) {
    this.logger = logger;
    this.plugins = plugins;
    this.possibleRestrictions = possibleRestrictions;
  }

  /**
   * For each plugin in {@link #plugins}, checks whether any of the
   * {@link #possibleRestrictions} were made for that specific version of
   * that specific plugin and, if so, constructs the {@code Restriction} and
   * registers it into {@code RestrictionHelper}.
   *
   * @param restrictionHelper the {@code RestrictionHelper} instance
   */
  public abstract void load(@NonNull H restrictionHelper);

}
