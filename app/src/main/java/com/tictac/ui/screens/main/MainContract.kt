package com.tictac.ui.screens.main

import androidx.compose.runtime.Immutable
import com.tictac.domain.model.Server

@Immutable
internal data class MainUiState(
    val isConnecting: Boolean = false,
    val selectedServer: Server? = null,
    val servers: List<Server> = emptyList(),
)

internal sealed interface MainIntent {
    data object LoadServers : MainIntent
    data class SelectServer(val server: Server) : MainIntent
    data object Connect : MainIntent
}

internal sealed interface MainEffect {
    data class ShowError(val message: String) : MainEffect
    data object NavigateToTicTacScreen : MainEffect
}
