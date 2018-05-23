package studio.lunabee.arn.vo

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Success(), data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Error(), data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Loading(), data, null)
        }
    }
}