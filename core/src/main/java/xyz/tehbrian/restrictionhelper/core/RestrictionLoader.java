package xyz.tehbrian.restrictionhelper.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import java.util.List;

/**
 * A utility class which registers {@link Restriction}s into a
 * {@link RestrictionHelper} instance according to {@link #plugins}.
 *
 * @param <P> the player type
 * @param <L> the location type
 * @param <R> the {@code Restriction} type
 * @param <H> the {@code RestrictionHelper} type
 * @param <T> the plugin type
 */
public abstract class RestrictionLoader<P, L, R extends Restriction<P, L>, H extends RestrictionHelper<P, L, R>, T> {

    /**
     * Used to log whether a check fails or passes, and why.
     */
    protected final Logger logger;
    /**
     * The plugins to check whether or not a certain {@code Restriction} from
     * {@link #possibleRestrictions} should be registered.
     */
    protected final List<T> plugins;
    /**
     * The {@code Restriction}s to maybe be registered.
     */
    protected final List<Class<R>> possibleRestrictions;

    /**
     * @param logger               the {@code Logger} used to log whether a check fails or
     *                             passes, and why
     * @param plugins              the plugins to check
     * @param possibleRestrictions the {@code Restriction}s to maybe be registered
     */
    public RestrictionLoader(
            final @NonNull Logger logger,
            final @NonNull List<T> plugins,
            final @NonNull List<Class<R>> possibleRestrictions) {
        this.logger = logger;
        this.plugins = plugins;
        this.possibleRestrictions = possibleRestrictions;
    }

    /**
     * For each plugin in {@link #plugins}, checks whether any of the
     * {@link #possibleRestrictions} were made for that specific version of
     * that specific plugin and, if so, constructs and registers the
     * {@code Restriction} into {@code RestrictionHelper}.
     *
     * @param restrictionHelper the {@code RestrictionHelper} instance
     */
    public abstract void load(@NonNull H restrictionHelper);
}
