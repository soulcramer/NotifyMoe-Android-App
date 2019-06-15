package app.soulcramer.arn

import android.app.Application
import app.soulcramer.arn.cache.cacheModule
import app.soulcramer.arn.di.appModule
import app.soulcramer.arn.remote.remoteModule
import com.github.ajalt.timberkt.Timber
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.threeten.bp.zone.ZoneRulesProvider

class NotifyMoe : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // Android context
            androidContext(this@NotifyMoe)
            // modules
            modules(listOf(remoteModule, cacheModule, appModule))
        }

        // Init ThreeTenABP
        AndroidThreeTen.init(this)

        // Query the ZoneRulesProvider so that it is loaded on a background coroutine
        GlobalScope.launch(Dispatchers.IO) {
            ZoneRulesProvider.getAvailableZoneIds()
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            /*StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            )
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            )*/
        }
    }

}
