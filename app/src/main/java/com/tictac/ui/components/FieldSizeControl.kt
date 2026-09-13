package com.tictac.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.ui.model.FieldSizeUi

/**
 * Square board previews, one per field size, laid out two per row. Selection is driven
 * entirely by [selected], so the caller (the MVI state) is the single source of truth.
 */
@Composable
internal fun FieldSizeControl(
    options: List<FieldSizeUi>,
    selected: FieldSizeUi,
    onSelect: (FieldSizeUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        options.chunked(TILES_PER_ROW).forEach { rowOptions ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                rowOptions.forEach { option ->
                    FieldSizeTile(
                        tileSize = option.cells,
                        isSelected = option == selected,
                        onClick = { onSelect(option) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                    )
                }
                // Keeps tiles in a short last row the same size as in a full one.
                repeat(TILES_PER_ROW - rowOptions.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private const val TILES_PER_ROW = 2

@PreviewLightDark
@Composable
private fun FieldSizeControlPreview() {
    TicTacTheme {
        var selected by remember { mutableStateOf(FieldSizeUi.Size3) }
        FieldSizeControl(
            options = FieldSizeUi.entries,
            selected = selected,
            onSelect = { selected = it },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
