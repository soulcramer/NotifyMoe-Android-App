package studio.lunabee.arn.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import studio.lunabee.arn.ui.HomeActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun userMainActivity(): HomeActivity
}
