package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.interactor.Result.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure<out T>(val exception: Exception, val data: T? = null) : Result<T>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure<*> -> "Failure[exception=$exception, data=$data]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded: Boolean
    get() {
        return this is Success && data != null
    }