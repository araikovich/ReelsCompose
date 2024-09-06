package com.example.thescore.compose.reelscompose.data.util

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    sealed class Error(val msg: String) : ResultWrapper<Nothing>() {
        data class ServerError(val httpCode: String, val message: String) : Error(message)
        data class ClientError(val httpCode: String, val errorCode: String, val message: String) : Error(message)
        data class UnknownError(val message: String) : Error(message)
        data class NetworkError(val message: String) : Error(message)

        object CancellationError : Error("StandaloneCoroutine was cancelled")
    }

    fun doOnResult(
        onSuccess: ((T) -> Unit)? = null,
        onServerError: ((error: Error.ServerError) -> Unit)? = null,
        onClientError: ((error: Error.ClientError) -> Unit)? = null,
        onNetworkError: ((error: Error.NetworkError) -> Unit)? = null,
        onUnknownError: ((error: Error.UnknownError) -> Unit)? = null,
        onError: ((error: Error) -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
    ): ResultWrapper<T> {
        if (this is Success) {
            onSuccess?.invoke(value)
        } else if (this is Error.ServerError && onServerError != null) {
            onServerError.invoke(this)
        } else if (this is Error.ClientError && onClientError != null) {
            onClientError.invoke(this)
        } else if (this is Error.NetworkError && onNetworkError != null) {
            onNetworkError.invoke(this)
        } else if (this is Error.UnknownError && onUnknownError != null) {
            onUnknownError.invoke(this)
        } else if (this is Error.CancellationError) {
            // nothing
        } else if (this is Error) {
            onError?.invoke(this)
        } else {
            throw IllegalStateException("Unknown state in ResultWrapper: $this")
        }
        onFinish?.invoke()
        return this
    }

    fun isSuccess(): Boolean = this is Success

    fun asSuccess(): Success<T> = this as Success<T>

    fun isError(): Boolean = this is Error

    fun asError(): Error = this as Error
}

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ResultWrapper<T> {
    return try {
        val response = call.invoke()
        val code = response.code()
        when {
            response.isSuccessful -> {
                ResultWrapper.Success(response.body()!!)
            }
            else -> {
                val errorBody = getErrorBody(response.errorBody())
                val httpCode = errorBody?.status ?: code.toString()
                val errorCode = errorBody?.code.toString()
                val errorMessage = errorBody?.message.orEmpty()

                if (code in 400..499) {
                    ResultWrapper.Error.ClientError(httpCode, errorCode, errorMessage)
                } else {
                    ResultWrapper.Error.ServerError(httpCode, errorMessage)
                }
            }
        }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        if (throwable is CancellationException) {
            ResultWrapper.Error.CancellationError
        } else {
            ResultWrapper.Error.UnknownError(throwable.message.orEmpty())
        }
    }
}

data class ErrorBodyResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("message") val message: String? = null
)

private fun getErrorBody(errorBody: ResponseBody?): ErrorBodyResponse? = Gson().fromJson(
    errorBody?.string(),
    ErrorBodyResponse::class.java
)

inline fun <SOURCE, RESULT> ResultWrapper<SOURCE>.map(mapper: (SOURCE) -> RESULT): ResultWrapper<RESULT> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(mapper(value))
        is ResultWrapper.Error -> this
    }
}