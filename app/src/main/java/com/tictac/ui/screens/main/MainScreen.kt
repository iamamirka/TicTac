package com.tictac.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark
import com.tictac.ui.components.TicTacBoardCell
import com.tictac.ui.components.TicTacButton

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.text)
            }
        }
    }

    MainScreenContent(
        state = state,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

@Composable
internal fun MainScreenContent(
    state: MainUiState,
    onIntent: (MainIntent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
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
                text = "TicTac",
                style = TicTacTheme.typography.display,
                color = colors.onBackground,
            )

            ScoreRow(
                scoreX = state.scoreX,
                scoreO = state.scoreO,
                onResetScore = { onIntent(MainIntent.ResetScoreClicked) },
            )

            StatusLine(state = state)

            Board(
                state = state,
                onCellClick = { index -> onIntent(MainIntent.CellClicked(index)) },
            )

            TicTacButton(
                text = if (state.isFinished) "New game" else "Restart",
                onClick = { onIntent(MainIntent.NewGameClicked) },
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun ScoreRow(
    scoreX: Int,
    scoreO: Int,
    onResetScore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacTheme.colors
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "X $scoreX",
            style = TicTacTheme.typography.titleMedium,
            color = colors.markX,
        )
        Text(
            text = "-",
            style = TicTacTheme.typography.titleMedium,
            color = colors.onSurfaceVariant,
        )
        Text(
            text = "$scoreO O",
            style = TicTacTheme.typography.titleMedium,
            color = colors.markO,
        )
        Text(
            text = "reset",
            style = TicTacTheme.typography.label,
            color = colors.onSurfaceVariant,
            modifier = Modifier
                .clip(TicTacTheme.shapes.small)
                .clickable(onClick = onResetScore)
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@Composable
private fun StatusLine(
    state: MainUiState,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacTheme.colors
    val result = state.result
    val text = when (result) {
        is GameResult.Win -> "${result.mark.name} wins!"
        GameResult.Draw -> "Draw - nobody wins"
        GameResult.InProgress -> "Turn: ${state.currentTurn.name}"
    }
    val color = when (result) {
        is GameResult.Win -> if (result.mark == Mark.X) colors.markX else colors.markO
        GameResult.Draw -> colors.onSurfaceVariant
        GameResult.InProgress -> colors.onSurfaceVariant
    }
    Text(
        text = text,
        style = TicTacTheme.typography.titleLarge,
        color = color,
        modifier = modifier,
    )
}

@Composable
private fun Board(
    state: MainUiState,
    onCellClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val winningLine = state.winningLine
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(GameBoard.SIZE) { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(GameBoard.SIZE) { column ->
                    val index = row * GameBoard.SIZE + column
                    TicTacBoardCell(
                        mark = state.board.markAt(index),
                        onClick = { onCellClick(index) },
                        isWinning = index in winningLine,
                        enabled = !state.isFinished,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun MainScreenInProgressPreview() {
    TicTacTheme {
        MainScreenContent(
            state = MainUiState(
                board = GameBoard(
                    listOf(
                        Mark.X, null, Mark.O,
                        null, Mark.X, null,
                        null, null, Mark.O,
                    ),
                ),
                currentTurn = Mark.X,
                scoreX = 1,
                scoreO = 2,
            ),
            onIntent = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun MainScreenWinPreview() {
    TicTacTheme {
        MainScreenContent(
            state = MainUiState(
                board = GameBoard(
                    listOf(
                        Mark.X, Mark.X, Mark.X,
                        Mark.O, Mark.O, null,
                        null, null, null,
                    ),
                ),
                result = GameResult.Win(Mark.X, listOf(0, 1, 2)),
                scoreX = 2,
                scoreO = 2,
            ),
            onIntent = {},
        )
    }
}
