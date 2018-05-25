package studio.lunabee.arn

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.github.ajalt.timberkt.Timber
import com.zhuinden.monarchy.Monarchy
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import studio.lunabee.arn.di.AppInjector
import javax.inject.Inject

class NotifyMoe : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Monarchy.init(this)
        AppInjector.init(this)
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
