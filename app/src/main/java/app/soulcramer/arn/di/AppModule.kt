package app.soulcramer.arn.di

import android.os.Debug
import androidx.room.Room
import app.soulcramer.arn.api.NotifyMoeService
import app.soulcramer.arn.db.NotifyMoeDatabase
import app.soulcramer.arn.repository.AnimeListRepository
import app.soulcramer.arn.repository.AnimeRepository
import app.soulcramer.arn.repository.UserRepository
import app.soulcramer.arn.ui.anime.AnimeDetailViewModel
import app.soulcramer.arn.ui.animelist.AnimeListViewModel
import app.soulcramer.arn.ui.dashboard.DashboardViewModel
import app.soulcramer.arn.ui.user.UserViewModel
import app.soulcramer.arn.util.LiveDataCallAdapterFactory
import app.soulcramer.arn.vo.animelist.AnimeListTypeAdapterFactory
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zhuinden.monarchy.Monarchy
import io.realm.RealmConfiguration
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule: Module = module {

    viewModel {
        AnimeListViewModel(get(), get())
    }

    viewModel {
        AnimeDetailViewModel(get())
    }

    viewModel {
        DashboardViewModel(get())
    }

    viewModel {
        UserViewModel(get())
    }

    single {
        AnimeListRepository(get(), get(), get())
    }

    single {
        UserRepository(get(), get())
    }

    single {
        AnimeRepository(get(), get())
    }

    single { get<NotifyMoeDatabase>().userDao() }
    single { get<NotifyMoeDatabase>().animeListDao() }
    single { get<NotifyMoeDatabase>().animeDao() }

    single {
        val builder = Room.databaseBuilder(get(), NotifyMoeDatabase::class.java, "notify.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return@single builder.build()
    }

    single<NotifyMoeService> {
        Retrofit.Builder()
            .baseUrl("https://notify.moe/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
            .create(NotifyMoeService::class.java)
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
    }

    single {
        Monarchy.Builder() //
            .setRealmConfiguration(get()) //
            .build()
    }

    single {
        RealmConfiguration.Builder() //
            .deleteRealmIfMigrationNeeded().build()
    }

    single {
        GsonBuilder()
            .registerTypeAdapterFactory(AnimeListTypeAdapterFactory())
            .create()
    }


    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
