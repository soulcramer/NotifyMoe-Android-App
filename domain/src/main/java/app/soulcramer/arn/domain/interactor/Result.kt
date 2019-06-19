package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.interactor.Result.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
@UseExperimental(ExperimentalContracts::class)
val Result<*>.succeeded: Boolean
    get() {
        contract {
            returns(true) implies (this@succeeded is Success<*>)
        }
        return this is Success && data != null
    }