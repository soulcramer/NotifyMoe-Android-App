package app.soulcramer.arn.remote

import app.soulcramer.arn.data.repository.UserDataStore
import app.soulcramer.arn.data.repository.UserRemote
import app.soulcramer.arn.data.source.UserRemoteDataStore
import app.soulcramer.arn.remote.mapper.UserEntityMapper
import app.soulcramer.arn.remote.mapper.UserModelMapper
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule: Module = module(override = true) {

    factory { UserEntityMapper() }
    factory { UserModelMapper() }

    single {
        NotifyMoeServiceFactory.makeNotifyMoeService(BuildConfig.DEBUG)
    }

    factory<UserRemote> { UserRemoteImpl(get(), get(), get()) }

    factory<UserDataStore>(named("remote")) { UserRemoteDataStore(get()) }
}