package studio.lunabee.arn.vo

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(var msg: String) : Resource<T>()
    data class Loading<out T>(var progress: Long = 0) : Resource<T>()
}


