package com.tictac.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tictac.R
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark
import com.tictac.ui.components.TicTacBoardCell
import com.tictac.ui.components.TicTacButton
import com.tictac.ui.components.TicTacButtonMode
import com.tictac.ui.components.TicTacButtonSize

@Composable
internal fun GameScreen(
    boardSize: Int = GameBoard.DEFAULT_SIZE,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(boardSize) {
        viewModel.onIntent(GameIntent.BoardSizeSelected(boardSize))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GameEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.text)
            }
        }
    }

    GameScreenContent(
        state = state,
        onIntent = viewModel::onIntent,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

@Composable
internal fun GameScreenContent(
    state: GameUiState,
    onIntent: (GameIntent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = { TopBar(onNavigateToSettings = {}) },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            ) {
                Text(
                    text = "TicTac",
                    style = TicTacTheme.typography.display,
                    color = TicTacTheme.colors.onBackground,
                )

                ScoreRow(
                    scoreX = state.scoreX,
                    scoreO = state.scoreO,
                    onResetScore = { onIntent(GameIntent.ResetScoreClicked) },
                )

                StatusLine(state = state)

                Board(
                    state = state,
                    onCellClick = { index -> onIntent(GameIntent.CellClicked(index)) },
                )

                TicTacButton(
                    text = if (state.isFinished) "New game" else "Restart",
                    onClick = { onIntent(GameIntent.NewGameClicked) },
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
    )
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
    state: GameUiState,
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
private fun TopBar(
    onNavigateToSettings: () -> Unit,
) {
    // Box, not a Row with weighted spacers: the coin button and the settings button
    // have different widths, so equal spacers would centre the title between them
    // rather than on the screen.
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Game",
            style = TicTacTheme.typography.titleMedium,
            color = TicTacTheme.colors.surface,
        )
        TicTacButton(
            text = "100",
            leadingIcon = painterResource(R.drawable.ic_coin1x_16),
            size = TicTacButtonSize.S,
            mode = TicTacButtonMode.Secondary,
            onClick = {},
            modifier = Modifier.align(Alignment.CenterStart),
        )
        TicTacButton(
            icon = painterResource(R.drawable.ic_settings_24),
            contentDescription = "Settings",
            size = TicTacButtonSize.S,
            mode = TicTacButtonMode.Secondary,
            onClick = onNavigateToSettings,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}

@Composable
private fun Board(
    state: GameUiState,
    onCellClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val winningLine = state.winningLine
    val size = state.board.size
    val metrics = boardMetricsFor(size)
    Column(
        modifier = modifier
            .widthIn(max = MAX_BOARD_WIDTH)
            .fillMaxWidth()
            .aspectRatio(1f),
        verticalArrangement = Arrangement.spacedBy(metrics.spacing),
    ) {
        repeat(size) { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(metrics.spacing),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                repeat(size) { column ->
                    val index = row * size + column
                    TicTacBoardCell(
                        mark = state.board.markAt(index),
                        onClick = { onCellClick(index) },
                        isWinning = index in winningLine,
                        enabled = !state.isFinished,
                        markStyle = metrics.markStyle,
                        borderWidth = metrics.borderWidth,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    )
                }
            }
        }
    }
}

private class BoardMetrics(
    val spacing: Dp,
    val borderWidth: Dp,
    val markStyle: TextStyle,
)

/** Gaps, borders and glyphs all have to shrink as the board gets denser. */
@Composable
private fun boardMetricsFor(size: Int): BoardMetrics {
    val typography = TicTacTheme.typography
    return when {
        size <= 3 -> BoardMetrics(8.dp, 2.dp, typography.mark)
        size <= 6 -> BoardMetrics(4.dp, 1.dp, typography.display)
        size <= 9 -> BoardMetrics(3.dp, 1.dp, typography.titleLarge)
        else -> BoardMetrics(2.dp, 1.dp, typography.label)
    }
}

private val MAX_BOARD_WIDTH = 400.dp

@PreviewLightDark
@Composable
private fun GameScreenInProgressPreview() {
    TicTacTheme {
        GameScreenContent(
            state = GameUiState(
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
private fun GameScreenWinPreview() {
    TicTacTheme {
        GameScreenContent(
            state = GameUiState(
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

@PreviewLightDark
@Composable
private fun GameScreenLargeBoardPreview() {
    TicTacTheme {
        GameScreenContent(
            state = GameUiState(
                board = GameBoard.empty(size = 12)
                    .withMark(0, Mark.X)
                    .withMark(13, Mark.O)
                    .withMark(26, Mark.X),
            ),
            onIntent = {},
        )
    }
}
