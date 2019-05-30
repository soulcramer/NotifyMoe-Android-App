package app.soulcramer.arn.remote

import app.soulcramer.arn.data.repository.UserDataStore
import app.soulcramer.arn.data.source.UserRemoteDataStore
import app.soulcramer.arn.remote.mapper.UserEntityMapper
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule: Module = module(override = true) {

    factory { UserEntityMapper() }

    single {
        NotifyMoeServiceFactory.makeNotifyMoeService(BuildConfig.DEBUG)
    }

    factory { UserRemoteImpl(get(), get()) }

    factory<UserDataStore>(named("remote")) { UserRemoteDataStore(get()) }
}