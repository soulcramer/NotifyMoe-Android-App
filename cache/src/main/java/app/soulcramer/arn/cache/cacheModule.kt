package app.soulcramer.arn.cache

import android.os.Debug
import androidx.room.Room
import app.soulcramer.arn.cache.mapper.UserEntityMapper
import app.soulcramer.arn.data.repository.UserCache
import app.soulcramer.arn.data.repository.UserDataStore
import app.soulcramer.arn.data.source.UserCacheDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cacheModule: Module = module(override = true) {
    factory { PreferencesHelper(androidContext()) }

    single {
        val builder = Room.databaseBuilder(androidContext(), NotifyMoeDatabase::class.java, "notify.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return@single builder.build()
    }

    single { get<NotifyMoeDatabase>().userDao() }

    factory { UserEntityMapper() }

    factory<UserCache> { UserCacheImpl(get(), get(), get()) }

    factory<UserDataStore>(named("local")) { UserCacheDataStore(get()) }
}