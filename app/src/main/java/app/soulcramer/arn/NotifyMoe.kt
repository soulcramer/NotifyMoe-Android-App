package app.soulcramer.arn

import android.app.Application
import app.soulcramer.arn.cache.cacheModule
import app.soulcramer.arn.di.appModule
import app.soulcramer.arn.remote.remoteModule
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class NotifyMoe : Application() {

    override fun onCreate() {
        super.onCreate()

        // this check is for RoboElectric tests that run in parallel so Koin gets set up multiple times
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger()
                // Android context
                androidContext(this@NotifyMoe)
                // modules
                modules(listOf(remoteModule, cacheModule, appModule))
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
