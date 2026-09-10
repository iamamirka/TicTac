package com.tictac.domain.model

/** Who the player is up against. */
public enum class GameMode {
    /** Single player - the second mark is played by the bot. */
    WithBot,

    /** Two players sharing one device. */
    WithFriend,
}
