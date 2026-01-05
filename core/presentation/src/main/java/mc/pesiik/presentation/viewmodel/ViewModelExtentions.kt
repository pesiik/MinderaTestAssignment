package mc.pesiik.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <T> ViewModel.launchCatching(
    block: suspend () -> T,
    onSuccess: (T) -> Unit = {},
    onError: (Throwable) -> Unit = {},
    onComplete: (Result<T>) -> Unit = {}
): Job = viewModelScope.launch {
    val result = runCatching { block() }
    result.fold(
        onSuccess = { onSuccess(it) },
        onFailure = { onError(it) }
    )
    onComplete(result)
}