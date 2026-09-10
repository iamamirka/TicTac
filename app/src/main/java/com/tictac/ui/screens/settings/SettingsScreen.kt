package com.tictac.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.ui.components.TicTacButton

@Composable
internal fun SettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    SettingsScreenContent(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

@Composable
internal fun SettingsScreenContent(
    state: SettingsUiState,
    onIntent: (SettingsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacTheme.colors
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = "Settings",
                style = TicTacTheme.typography.display,
                color = colors.onBackground,
            )
            Text(
                text = if (state.isLoading) "Loading..." else "Nothing to configure yet",
                style = TicTacTheme.typography.bodyLarge,
                color = colors.onSurfaceVariant,
            )
            TicTacButton(
                text = "Back",
                onClick = { onIntent(SettingsIntent.BackClicked) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenPreview() {
    TicTacTheme {
        SettingsScreenContent(state = SettingsUiState(), onIntent = {})
    }
}
