package com.tictac.domain.model

/** How hard the bot plays. Only meaningful for [GameMode.WithBot]. */
public enum class BotDifficulty {
    /** Picks a random free cell. */
    EasyMode,

    /** Plays the optimal move. */
    HardMode,
}
