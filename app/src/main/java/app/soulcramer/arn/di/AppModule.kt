package app.soulcramer.arn.di

import app.soulcramer.arn.data.UserDataRepository
import app.soulcramer.arn.data.mapper.UserMapper
import app.soulcramer.arn.data.source.UserDataStoreFactory
import app.soulcramer.arn.domain.interactor.user.GetUser
import app.soulcramer.arn.domain.interactor.user.HasLoggedUser
import app.soulcramer.arn.domain.interactor.user.LogInUser
import app.soulcramer.arn.domain.interactor.user.LogOutUser
import app.soulcramer.arn.domain.interactor.user.SearchUsers
import app.soulcramer.arn.domain.prefs.PreferenceStorage
import app.soulcramer.arn.domain.repository.UserRepository
import app.soulcramer.arn.prefs.SharedPreferenceStorage
import app.soulcramer.arn.ui.session.SessionViewModel
import app.soulcramer.arn.ui.user.UserViewModel
import app.soulcramer.arn.ui.user.list.UserListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module(override = true) {

    factory { UserDataStoreFactory(get(), get(named("local")), get(named("remote"))) }

    factory { UserMapper() }

    factory<UserRepository> { UserDataRepository(get(), get()) }

    single<PreferenceStorage> { SharedPreferenceStorage(androidContext()) }

    factory { HasLoggedUser(get()) }
    factory { LogInUser(get()) }
    factory { LogOutUser(get()) }
    factory { GetUser(get()) }
    factory { SearchUsers(get()) }

    viewModel { UserViewModel(get()) }

    viewModel { SessionViewModel(get(), get(), get()) }

    viewModel { UserListViewModel(get()) }
}
