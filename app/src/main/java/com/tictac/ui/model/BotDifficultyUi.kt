package com.tictac.ui.model

import com.tictac.domain.model.BotDifficulty
import com.tictac.ui.components.SegmentOption

/** UI twin of [BotDifficulty] - carries the label each difficulty is presented with. */
internal enum class BotDifficultyUi(val domain: BotDifficulty) : SegmentOption {
    EasyMode(BotDifficulty.EasyMode),
    HardMode(BotDifficulty.HardMode);

    override fun text(): String = when (this) {
        EasyMode -> "Easy"
        HardMode -> "Hard"
    }
}

internal fun BotDifficulty.toUi(): BotDifficultyUi = when (this) {
    BotDifficulty.EasyMode -> BotDifficultyUi.EasyMode
    BotDifficulty.HardMode -> BotDifficultyUi.HardMode
}
