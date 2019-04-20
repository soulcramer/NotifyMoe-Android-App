package app.soulcramer.arn

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import app.soulcramer.arn.di.appModule
import com.github.ajalt.timberkt.Timber
import com.zhuinden.monarchy.Monarchy
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import javax.inject.Inject

class NotifyMoe : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

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
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            )
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
            )
        }
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
