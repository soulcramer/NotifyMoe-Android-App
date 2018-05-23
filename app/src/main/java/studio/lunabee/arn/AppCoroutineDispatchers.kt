package studio.lunabee.arn

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppCoroutineDispatchers(
    val disk: CoroutineDispatcher,
    val network: CoroutineDispatcher,
    val main: CoroutineDispatcher) {

    @Inject
    constructor() : this(
        Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
        Executors.newFixedThreadPool(3).asCoroutineDispatcher(),
        MainThreadExecutor().asCoroutineDispatcher()
    )

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}