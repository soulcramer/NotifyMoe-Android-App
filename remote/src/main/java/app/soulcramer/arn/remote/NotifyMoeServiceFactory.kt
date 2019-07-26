package app.soulcramer.arn.remote

import app.soulcramer.arn.remote.util.IsoDurationJsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.Duration

/**
 * Provide "make" methods to create instances of [NotifyMoeServiceFactory]
 * and its related dependencies, such as OkHttpClient, Moshi, etc.
 */
object NotifyMoeServiceFactory {

    fun makeNotifyMoeService(isDebug: Boolean): NotifyMoeService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug))
        return makeBufferooService(okHttpClient, makeMoshi())
    }

    private fun makeBufferooService(okHttpClient: OkHttpClient, moshi: Moshi): NotifyMoeService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://notify.moe/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create()
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(Duration.ofMinutes(2))
            .readTimeout(Duration.ofMinutes(2))
            .build()
    }

    private fun makeMoshi(): Moshi {
        return Moshi.Builder().run {
            add(IsoDurationJsonAdapter())
            build()
        }
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}