package studio.lunabee.arn.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import studio.lunabee.arn.ui.anime.AnimeDetailFragment
import studio.lunabee.arn.ui.animelist.AnimeListFragment
import studio.lunabee.arn.ui.dashboard.DashboardFragment
import studio.lunabee.arn.ui.settings.SettingsFragment
import studio.lunabee.arn.ui.user.UserFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeAnimeListFragment(): AnimeListFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeAnimeDetailFragment(): AnimeDetailFragment
}
