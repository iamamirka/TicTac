package com.tictac.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.tictac.R
import com.tictac.domain.model.GameMode
import com.tictac.ui.components.SegmentOption

/** UI twin of [GameMode] - carries the label and icon each mode is presented with. */
internal enum class GameModeUi(val domain: GameMode) : SegmentOption {
    WithBot(GameMode.WithBot),
    WithFriend(GameMode.WithFriend),
    ;

    @Composable
    override fun icon(): Painter = painterResource(
        when (this) {
            WithBot -> R.drawable.ic_bot_24
            WithFriend -> R.drawable.ic_user_24
        },
    )

    override fun text(): String = when (this) {
        WithBot -> "With bot"
        WithFriend -> "With friend"
    }
}

internal fun GameMode.toUi(): GameModeUi = when (this) {
    GameMode.WithBot -> GameModeUi.WithBot
    GameMode.WithFriend -> GameModeUi.WithFriend
}
