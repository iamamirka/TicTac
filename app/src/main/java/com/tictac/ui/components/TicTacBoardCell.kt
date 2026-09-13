package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tictac.R
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.Mark

/**
 * One square of the board. The caller sizes it through [modifier], since a cell on a
 * 12x12 board is a fraction of a cell on a 3x3 one.
 */
@Composable
public fun TicTacBoardCell(
    mark: Mark?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isWinning: Boolean = false,
    enabled: Boolean = true,
    markStyle: TextStyle = TicTacTheme.typography.mark,
    borderWidth: Dp = 2.dp,
) {
    val colors = TicTacTheme.colors
    val backgroundColor = when(mark) {
        Mark.X -> colors.markX
        Mark.O -> colors.markO
        else -> colors.surface
    }
    Box(
        modifier = modifier
            .clip(TicTacTheme.shapes.boardCell)
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = colors.boardGrid,
                shape = TicTacTheme.shapes.boardCell,
            )
            .clickable(enabled = enabled && mark == null, onClick = onClick)
            .semantics { contentDescription = mark?.name ?: "empty cell" },
        contentAlignment = Alignment.Center,
    ) {
        if (mark != null) {
            Icon(
                painter = painterResource(if (mark == Mark.X) R.drawable.ic_x_48 else R.drawable.ic_o_48),
                tint = TicTacTheme.colors.onAccent,
                contentDescription = "",
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacBoardCellPreview() {
    TicTacTheme {
        TicTacBoardCell(mark = Mark.X, onClick = {}, modifier = Modifier.size(88.dp))
    }
}
