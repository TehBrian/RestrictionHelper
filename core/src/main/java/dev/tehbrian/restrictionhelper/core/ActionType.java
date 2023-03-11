package dev.tehbrian.restrictionhelper.core;

/**
 * Types of actions that a player can perform.
 */
public enum ActionType {
  /**
   * Wildcard. If a player has permission to perform this {@code ActionType},
   * then they have permission to perform any other {@code ActionType}.
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
   * Non-destructively interacting with a block. This includes actions
   * such as opening and closing doors, stepping on pressure plates,
   * and pressing a button.
   */
  INTERACT,
}
