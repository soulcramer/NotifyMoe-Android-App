package studio.lunabee.arn.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import studio.lunabee.arn.ui.animelist.AnimeListViewModel
import studio.lunabee.arn.ui.dashboard.DashboardViewModel
import studio.lunabee.arn.ui.user.UserViewModel
import studio.lunabee.arn.viewmodel.NotifyMoeViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnimeListViewModel::class)
    abstract fun bindAnimeListViewModel(animeListViewModel: AnimeListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: NotifyMoeViewModelFactory): ViewModelProvider.Factory
}
