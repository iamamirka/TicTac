package com.tictac.ui.model

internal enum class PlayerType {
    PLAYER,
    BOT;

    fun text() = when (this) {
        PLAYER -> "Player";
        BOT -> "AI"
    }
}