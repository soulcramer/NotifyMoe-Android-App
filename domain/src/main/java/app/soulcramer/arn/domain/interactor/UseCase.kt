package app.soulcramer.arn.domain.interactor

import androidx.paging.PagedList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using a [CoroutineDispatcher].
 */
abstract class UseCase<in P, R> {

    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    /**
     * Executes the use case asynchronously and places the [Result] in a MutableLiveData
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        // result.value = Result.Loading add data to Loading to avoid glitches
        return try {
            withContext(dispatcher) {
                try {
                    execute(parameters).let { useCaseResult ->
                        Result.Success(useCaseResult)
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    Timber.e(e)
                    Result.Failure<R>(e)
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

/**
 * Executes business logic synchronously or asynchronously using a [CoroutineDispatcher].
 */
abstract class SuspendingUseCase<in P, R> {

    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    /**
     * Executes the use case asynchronously and places the [Result] in a MutableLiveData
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        return withContext(dispatcher) {
            execute(parameters)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): Result<R>
}

/**
 * Executes business logic synchronously or asynchronously using a [CoroutineDispatcher].
 */
abstract class PagingUseCase<in P : PagingUseCase.Parameters<T>, T> : SuspendingUseCase<P, PagedList<T>>() {
    interface Parameters<T> {
        val pagingConfig: PagedList.Config
        val boundaryCallback: PagedList.BoundaryCallback<T>?
    }
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): Result<R> = this(Unit)
suspend operator fun <R> SuspendingUseCase<Unit, R>.invoke(): Result<R> = this(Unit)
