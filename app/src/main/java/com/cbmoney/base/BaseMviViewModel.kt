package com.cbmoney.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseMviViewModel <S: MviState, E: MviEvent, I: MviIntent>: ViewModel(){
    private val _viewState: MutableStateFlow<S> = MutableStateFlow(initialState())
    val viewState: StateFlow<S> = _viewState.asStateFlow()
    private val _eventChannel: Channel<E> = Channel(Channel.UNLIMITED)
    val singleEvent = _eventChannel.receiveAsFlow()
    init {
        Log.d("BaseMviViewModel", "init: ViewModel ${this.javaClass.simpleName}")
    }
    protected abstract fun initialState(): S
    abstract fun processIntent(intent: I)
    protected fun updateState(newState: S) {
        _viewState.value = newState
    }
    protected fun updateState(reducer: S.() -> S){
        _viewState.update { it.reducer() }
    }

    protected fun sendEvent(event: E){
        viewModelScope.launch {
            _eventChannel.trySend(event)
        }
    }
    protected val currentState: S
        get() = _viewState.value
    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Log.d("BaseMviViewModel", "onCleared: ViewModel ${this.javaClass.simpleName} cleared")
        _eventChannel.close()
    }
}