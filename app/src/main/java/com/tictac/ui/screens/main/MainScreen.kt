package com.tictac.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tictac.R
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.BotDifficulty
import com.tictac.domain.model.GameMode
import com.tictac.ui.components.TicTacButton
import com.tictac.ui.components.TicTacButtonMode
import com.tictac.ui.components.TicTacButtonSize
import com.tictac.ui.components.TicTacSegmentedControl
import com.tictac.ui.model.BotDifficultyUi
import com.tictac.ui.model.GameModeUi

@Composable
internal fun MainScreen(
    onNavigateToGame: (GameMode, BotDifficulty) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainEffect.NavigateToGame ->
                    onNavigateToGame(effect.gameMode, effect.botDifficulty)

                MainEffect.NavigateToSettings -> onNavigateToSettings()
            }
        }
    }

    MainScreenContent(
        state = state,
        onIntent = viewModel::onIntent,
        onNavigateToSettings = onNavigateToSettings,
        modifier = modifier,
    )
}

@Composable
internal fun MainScreenContent(
    state: MainUiState,
    onIntent: (MainIntent) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacTheme.colors
    Scaffold(
        topBar = {
            TopBar(onNavigateToSettings = onNavigateToSettings)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            ) {
                TicTacSegmentedControl(
                    options = GameModeUi.entries,
                    selected = state.gameMode,
                    onSelect = { onIntent(MainIntent.GameModeSelected(it)) },
                    modifier = Modifier.controlWidth(),
                )
                TicTacSegmentedControl(
                    options = BotDifficultyUi.entries,
                    selected = state.botDifficulty,
                    onSelect = { onIntent(MainIntent.DifficultySelected(it)) },
                    enabled = state.isDifficultyEnabled,
                    modifier = Modifier.controlWidth(),
                )

                TicTacButton(
                    text = "Start game",
                    onClick = { onIntent(MainIntent.StartGame(state)) },
                )

                TicTacButton(
                    text = "Settings",
                    onClick = { onIntent(MainIntent.SettingsClicked) },
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(colors.background),
    )
}

@Composable
private fun TopBar(
    onNavigateToSettings: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TicTacButton(
            text = "100",
            leadingIcon = painterResource(R.drawable.ic_coin1x_16),
            size = TicTacButtonSize.S,
            mode = TicTacButtonMode.Secondary,
            onClick = {},
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Game",
            style = TicTacTheme.typography.titleMedium,
            color = TicTacTheme.colors.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.weight(1f))
        TicTacButton(
            icon = painterResource(R.drawable.ic_settings_24),
            contentDescription = "Settings",
            size = TicTacButtonSize.S,
            mode = TicTacButtonMode.Secondary,
            onClick = onNavigateToSettings
        )
    }
}

@Composable
private fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = TicTacTheme.typography.label,
        color = TicTacTheme.colors.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

private fun Modifier.controlWidth(): Modifier = fillMaxWidth().widthIn(max = 360.dp)

@PreviewLightDark
@Composable
private fun MainScreenWithBotPreview() {
    TicTacTheme {
        MainScreenContent(
            state = MainUiState(
                gameMode = GameModeUi.WithBot,
                botDifficulty = BotDifficultyUi.HardMode,
            ),
            onIntent = {},
            onNavigateToSettings = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun MainScreenWithFriendPreview() {
    TicTacTheme {
        MainScreenContent(
            state = MainUiState(gameMode = GameModeUi.WithFriend),
            onIntent = {},
            onNavigateToSettings = {}
        )
    }
}
