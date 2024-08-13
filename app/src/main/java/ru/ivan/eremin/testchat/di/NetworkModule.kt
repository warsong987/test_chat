package ru.ivan.eremin.testchat.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.ivan.eremin.testchat.app.config.AppApi
import ru.ivan.eremin.testchat.di.qualifierqualifier.interceptor.AppInterceptor
import ru.ivan.eremin.testchat.di.qualifierqualifier.interceptor.NetworkInterceptor
import ru.ivan.eremin.testchat.network.error.ApiExceptionAdapter
import ru.ivan.eremin.testchat.network.interceptor.ServiceAuthenticator
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun cache(@ApplicationContext context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

    @Singleton
    @Provides
    fun json(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Singleton
    @Provides
    fun jsonConverter(json: Json): Converter.Factory{
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    fun baseOkHttp(
        cache: Cache,
        @AppInterceptor interceptors: Set<@JvmSuppressWildcards Interceptor>,
        @NetworkInterceptor networkInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
            .cache(cache)

        interceptors
            .forEach {
                builder.addInterceptor(it)
            }

        networkInterceptors
            .forEach {
                builder.addNetworkInterceptor(it)
            }

        return builder
    }

    @Provides
    fun backendOkHttp(
        baseOkHttp: OkHttpClient.Builder,
        serviceAuthenticator: ServiceAuthenticator
    ): OkHttpClient {
        return baseOkHttp
            .addInterceptor(serviceAuthenticator)
            .build()
    }

    @Provides
    fun apiBackend(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        apiExceptionAdapter: ApiExceptionAdapter,
        appApi: AppApi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(appApi.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(apiExceptionAdapter)
        .build()

    companion object {
        private const val CACHE_SIZE = 5242880L // 5 MiB
        private const val TIMEOUT_SECONDS = 30L
        private const val PING_INTERVAL = 20L
    }
}