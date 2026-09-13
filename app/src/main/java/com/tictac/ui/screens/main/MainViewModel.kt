package com.tictac.ui.screens.main

import com.tictac.core.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor() :
    MviViewModel<MainUiState, MainIntent, MainEffect>(MainUiState()) {

    override fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.GameModeSelected -> updateState { copy(gameMode = intent.gameMode) }
            is MainIntent.DifficultySelected -> updateState { copy(botDifficulty = intent.botDifficulty) }
            is MainIntent.FieldSizeSelected -> updateState { copy(fieldSize = intent.fieldSize) }
            MainIntent.SettingsClicked -> handleEffect(MainEffect.NavigateToSettings)
            is MainIntent.StartGame -> onStartGame(intent.uiState)
        }
    }

    private fun onStartGame(uiState: MainUiState) {
        updateState { uiState }
        handleEffect(
            MainEffect.NavigateToGame(
                gameMode = uiState.gameMode.domain,
                botDifficulty = uiState.botDifficulty.domain,
                boardSize = uiState.fieldSize.cells,
            ),
        )
    }
}
