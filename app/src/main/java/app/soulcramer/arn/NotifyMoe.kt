package app.soulcramer.arn

import android.app.Application
import app.soulcramer.arn.di.appModule
import com.github.ajalt.timberkt.Timber
import com.zhuinden.monarchy.Monarchy
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NotifyMoe : Application() {

    override fun onCreate() {
        super.onCreate()
        Monarchy.init(this)
        startKoin {
            androidLogger()
            // Android context
            androidContext(this@NotifyMoe)
            // modules
            modules(appModule)
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
