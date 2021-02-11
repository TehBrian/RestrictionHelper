package xyz.tehbrian.restrictionhelper;

/**
 * Various types of actions that a player can perform.
 * This list is intentionally non-exhaustive to prevent
 * restriction abstraction from becoming too specific to one plugin.
 */
public enum ActionType {
    /**
     * Wildcard. If a player has permission to perform this ActionType,
     * they have permission to perform any other ActionType.
     */
    ALL,
    /**
     * Placing a block.
     */
    PLACE,
    /**
     * Breaking a block.
     */
    BREAK,
    /**
     * Non-destructively interacting with a block. This includes
     * things such as opening and closing doors, stepping on pressure plates,
     * pressing a button, etc.
     */
    INTERACT,
    /**
     * Destructively interacting with a block. This includes rotating
     * a block, changing the items of an inventory, etc.
     */
    MODIFY,
}
