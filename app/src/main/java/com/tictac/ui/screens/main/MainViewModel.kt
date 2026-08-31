package com.tictac.ui.screens.main

import com.tictac.core.mvi.MviViewModel
import com.tictac.domain.usecase.ConnectTicTacUseCase
import com.tictac.domain.usecase.GetAvailableServersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getAvailableServersUseCase: GetAvailableServersUseCase,
    private val connectTicTacUseCase: ConnectTicTacUseCase
) : MviViewModel<MainUiState, MainIntent, MainEffect>(MainUiState()) {

    override fun onIntent(intent: MainIntent) {
        TODO("Not yet implemented")
    }


}
