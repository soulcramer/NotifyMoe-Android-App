package app.soulcramer.arn.di

import android.content.Context
import app.soulcramer.arn.ui.anime.AnimeDetailFragment
import app.soulcramer.arn.ui.anime.AnimeDetailViewModel
import app.soulcramer.arn.ui.anime.AnimeDetailsTextCreator
import app.soulcramer.arn.ui.animelist.AnimeListViewModel
import app.soulcramer.arn.ui.dashboard.DashboardViewModel
import app.soulcramer.arn.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module {

    viewModel {
        AnimeListViewModel(get(), get())
    }

    viewModel { (animeId: String) ->
        AnimeDetailViewModel(get(), animeId)
    }

    viewModel {
        DashboardViewModel(get())
    }

    viewModel {
        UserViewModel(get())
    }

    scope(named<AnimeDetailFragment>()) {
        scoped { (context: Context) -> AnimeDetailsTextCreator(context) }
    }
}
