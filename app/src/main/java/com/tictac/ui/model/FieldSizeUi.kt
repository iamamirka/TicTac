package com.tictac.ui.model

/**
 * Field sizes offered on the main screen. UI-only for now - the domain board is
 * still fixed at 3x3, so there is no domain twin to map to yet.
 */
internal enum class FieldSizeUi(val cells: Int) {
    Size3(3),
    Size6(6),
    Size9(9),
    Size12(12),
}
