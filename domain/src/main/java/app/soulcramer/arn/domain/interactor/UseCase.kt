package app.soulcramer.arn.domain.interactor

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
    suspend operator fun invoke(parameters: P): Result<R> {
        // result.value = Result.Loading add data to Loading to avoid glitches
        return try {
            withContext(dispatcher) {
                try {
                    execute(parameters).let { useCaseResult ->
                        Result.Success(useCaseResult)
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    Result.Failure(e)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            Result.Failure(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): Result<R> = this(Unit)