package com.tictac.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class MviViewModel<S, I, E>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.Factory.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    abstract fun onIntent(intent: I)

    protected fun updateState(reduce: S.() -> S) {
        _state.update { it.reduce() }
    }

    protected fun handleEffect(effect: E) {
        viewModelScope.launch { _effect.send(effect) }
    }
}