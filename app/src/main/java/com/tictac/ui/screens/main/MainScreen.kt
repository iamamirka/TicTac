package com.tictac.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.ui.components.Mark
import com.tictac.ui.components.TicTacBoardCell
import com.tictac.ui.components.TicTacButton

@Composable
public fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val board = remember { mutableStateListOf<Mark?>().apply { repeat(9) { add(null) } } }
    var turn by remember { mutableStateOf(Mark.X) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = TicTacTheme.colors.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = "TicTac",
            style = TicTacTheme.typography.display,
            color = TicTacTheme.colors.onBackground,
        )
        Text(
            text = "Turn: ${turn.name}",
            style = TicTacTheme.typography.bodyMedium,
            color = TicTacTheme.colors.onSurfaceVariant,
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) { column ->
                        val index = row * 3 + column
                        TicTacBoardCell(
                            mark = board[index],
                            onClick = {
                                board[index] = turn
                                turn = if (turn == Mark.X) Mark.O else Mark.X
                            },
                        )
                    }
                }
            }
        }
        TicTacButton(
            text = "New game",
            onClick = {
                repeat(9) { board[it] = null }
                turn = Mark.X
            },
        )
    }
}

@PreviewLightDark
@Composable
private fun MainScreenPreview() {
    TicTacTheme {
        MainScreen()
    }
}
