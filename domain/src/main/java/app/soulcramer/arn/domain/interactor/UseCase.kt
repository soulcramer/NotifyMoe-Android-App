package app.soulcramer.arn.domain.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.soulcramer.arn.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using a [Scheduler].
 */
abstract class UseCase<in P, R> {

    private val dispatcher = Dispatchers.IO

    /** Executes the use case asynchronously and places the [Result] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     *
     */
    suspend operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) {
        // result.value = Result.Loading TODO: add data to Loading to avoid glitches
        try {
            withContext(dispatcher) {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Result.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            result.postValue(Result.Error(e))
        }
    }

    /** Executes the use case asynchronously and returns a [Result] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    /** Executes the use case synchronously  */
    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)
suspend operator fun <R> UseCase<Unit, R>.invoke(result: MutableLiveData<Result<R>>): Unit = this(Unit, result)