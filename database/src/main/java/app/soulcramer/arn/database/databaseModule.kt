package app.soulcramer.arn.database

import android.os.Debug
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single { get<NotifyMoeDatabase>().userDao() }
    single { get<NotifyMoeDatabase>().animeListDao() }
    single { get<NotifyMoeDatabase>().animeDao() }

    single {
        val builder = Room.databaseBuilder(androidContext(), NotifyMoeDatabase::class.java, "notify.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return@single builder.build()
    }
}