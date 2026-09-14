package com.tictac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.BotDifficulty
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameMode
import com.tictac.ui.screens.game.GameScreen
import com.tictac.ui.screens.main.MainScreen
import com.tictac.ui.screens.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Consume what the outer Scaffold already padded, so screens with their
                    // own inset-aware Scaffold do not apply the system bars a second time.
                    TicTacApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    )
                }
            }
        }
    }
}

/** Which screen is on top. Replaced by a real nav graph once there is more than one flow. */
private enum class Screen {
    Main,
    Game,
    Settings,
}

@Composable
private fun TicTacApp(modifier: Modifier = Modifier) {
    var screen by rememberSaveable { mutableStateOf(Screen.Main) }
    var boardSize by rememberSaveable { mutableIntStateOf(GameBoard.DEFAULT_SIZE) }

    BackHandler(enabled = screen != Screen.Main) { screen = Screen.Main }

    when (screen) {
        Screen.Main -> MainScreen(
            onNavigateToGame = { _: GameMode, _: BotDifficulty, selectedSize: Int ->
                boardSize = selectedSize
                screen = Screen.Game
            },
            onNavigateToSettings = { screen = Screen.Settings },
            modifier = modifier,
        )

        Screen.Game -> GameScreen(
            onNavigateBack = { screen = Screen.Main },
            onNavigateToSettings = { screen = Screen.Settings },
            boardSize = boardSize,
            modifier = modifier,
        )

        Screen.Settings -> SettingsScreen(
            onNavigateBack = { screen = Screen.Main },
            modifier = modifier,
        )
    }
}
