package ru.snowmaze.domain

sealed class Result<T> {

    class Success<T>(val value: T) : Result<T>()

    class Failure<T>(val error: Throwable) : Result<T>()

    inline fun fold(onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        if (this is Success) {
            onSuccess(value)
        }
        if (this is Failure) onFailure(error)
    }

    inline fun onSuccess(onSuccess: (T) -> Unit) {
        if (this is Success) onSuccess(value)
    }

    inline fun onFailure(onFailure: (Throwable) -> Unit) {
        if (this is Failure) onFailure(error)
    }

    companion object {

        fun <T> success(value: T) = Success(value)

        fun <T> failure(error: Throwable) = Failure<T>(error)

        suspend fun <T> runSuspending(block: suspend () -> T): Result<T> = try {
            success(block())
        } catch (e: Throwable) {
            failure(e)
        }

        fun <T> run(block: () -> T): Result<T> = try {
            success(block())
        } catch (e: Throwable) {
            failure(e)
        }

    }

}