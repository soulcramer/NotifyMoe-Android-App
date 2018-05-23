package studio.lunabee.arn.vo

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
sealed class Status

class Success : Status()
class Error : Status()
class Loading : Status()
