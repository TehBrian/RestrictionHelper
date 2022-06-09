package xyz.tehbrian.restrictionhelper.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestrictionInfo {

    /**
     * The name of the plugin to which this restriction is applicable.
     *
     * @return the name
     */
    String name();

    /**
     * The version of the plugin to which this restriction is applicable.
     *
     * @return the version
     */
    String version();

    /**
     * The fully-qualified name of the main class of the plugin to which this
     * restriction is applicable.
     *
     * @return the main class
     */
    String mainClass();

}
