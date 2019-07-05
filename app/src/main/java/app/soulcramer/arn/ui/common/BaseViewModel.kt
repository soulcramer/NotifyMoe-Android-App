package app.soulcramer.arn.ui.common

import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<A : BaseAction, S : BaseState>(private val initialState: S) : ViewModel() {
    private val internalState = MutableLiveData<S>().apply { value = initialState }

    val state: LiveData<S> = internalState

    protected abstract suspend fun onHandle(action: A)

    fun handle(action: A) = viewModelScope.launch {
        onHandle(action)
    }

    @MainThread
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun updateState(reducer: (S) -> S) {
        val currentState = state.value ?: initialState

        internalState.value = reducer(currentState)
    }
}

interface BaseAction
interface BaseState

sealed class ViewState
object Loading : ViewState()
object Data : ViewState()
object Empty : ViewState()
object Error : ViewState()
object NetworkError : ViewState()