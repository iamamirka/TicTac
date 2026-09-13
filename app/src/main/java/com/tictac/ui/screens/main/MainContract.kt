package com.tictac.ui.screens.main

import androidx.compose.runtime.Immutable
import com.tictac.domain.model.BotDifficulty
import com.tictac.domain.model.GameMode
import com.tictac.ui.model.BotDifficultyUi
import com.tictac.ui.model.FieldSizeUi
import com.tictac.ui.model.GameModeUi

@Immutable
internal data class MainUiState(
    val gameMode: GameModeUi = GameModeUi.WithBot,
    val botDifficulty: BotDifficultyUi = BotDifficultyUi.EasyMode,
    val fieldSize: FieldSizeUi = FieldSizeUi.Size3,
) {
    /** Difficulty only applies when a bot is playing. */
    val isDifficultyEnabled: Boolean get() = gameMode == GameModeUi.WithBot
}

internal sealed interface MainIntent {
    data class GameModeSelected(val gameMode: GameModeUi) : MainIntent
    data class DifficultySelected(val botDifficulty: BotDifficultyUi) : MainIntent
    data class FieldSizeSelected(val fieldSize: FieldSizeUi) : MainIntent
    data object SettingsClicked : MainIntent
    data class StartGame(val uiState: MainUiState) : MainIntent
}

internal sealed interface MainEffect {
    data class NavigateToGame(
        val gameMode: GameMode,
        val botDifficulty: BotDifficulty,
    ) : MainEffect

    data object NavigateToSettings : MainEffect
}
