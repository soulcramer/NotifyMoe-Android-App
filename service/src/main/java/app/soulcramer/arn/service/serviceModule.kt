package app.soulcramer.arn.service

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule: Module = module {

    single<NotifyMoeService> {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(get())
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapterFactory(AnimeListTypeAdapterFactory())
            .create()

        Retrofit.Builder()
            .baseUrl("https://notify.moe/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(app.soulcramer.arn.service.util.LiveDataCallAdapterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okhttp)
            .build()
            .create(NotifyMoeService::class.java)
    }

}