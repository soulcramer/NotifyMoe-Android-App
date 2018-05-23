package studio.lunabee.arn.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.zhuinden.monarchy.Monarchy
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import studio.lunabee.arn.api.NotifyMoeService
import studio.lunabee.arn.db.UserDao
import studio.lunabee.arn.db.userDao
import studio.lunabee.arn.util.LiveDataCallAdapterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): NotifyMoeService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
            .baseUrl("https://notify.moe/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(NotifyMoeService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDao(monarchy: Monarchy): UserDao = monarchy.userDao()

    @Provides
    @Singleton
    fun realmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder() //
            .deleteRealmIfMigrationNeeded().build()
    }

    @Provides
    @Singleton
    fun monarchy(realmConfiguration: RealmConfiguration): Monarchy {
        return Monarchy.Builder() //
            .setRealmConfiguration(realmConfiguration) //
            .build()
    }
}