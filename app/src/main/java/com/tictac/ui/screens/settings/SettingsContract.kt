package com.tictac.ui.screens.settings

import androidx.compose.runtime.Immutable

/** Placeholder - settings have no content yet. */
@Immutable
internal data class SettingsUiState(
    val isLoading: Boolean = false,
)

internal sealed interface SettingsIntent {
    data object BackClicked : SettingsIntent
}

internal sealed interface SettingsEffect {
    data object NavigateBack : SettingsEffect
}
