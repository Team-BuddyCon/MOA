package com.yapp.buddycon.gifticon.available.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.yapp.buddycon.domain.result.DataResult
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> HandleDataResult(
    dataResultStateFlow: StateFlow<DataResult<T>>,
    onSuccess: (DataResult.Success<T>) -> Unit,
    onFailure: (DataResult.Failure) -> Unit,
    onLoading: (DataResult.Loading) -> Unit
) {
    val dataResult = dataResultStateFlow.collectAsState().value

    LaunchedEffect(dataResult) {
        when(dataResult) {
            is DataResult.Success -> {
                onSuccess(dataResult)
            }

            is DataResult.Failure -> {
                onFailure(dataResult)
            }

            is DataResult.Loading -> {
                onLoading(dataResult)
            }

            is DataResult.None -> {}
        }
    }
}