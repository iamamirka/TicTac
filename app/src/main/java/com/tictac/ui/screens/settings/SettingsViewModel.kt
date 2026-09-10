package com.tictac.ui.screens.settings

import com.tictac.core.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor() :
    MviViewModel<SettingsUiState, SettingsIntent, SettingsEffect>(SettingsUiState()) {

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            SettingsIntent.BackClicked -> handleEffect(SettingsEffect.NavigateBack)
        }
    }
}
