package ru.ivan.eremin.testchat.di

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ivan.eremin.testchat.BuildConfig
import ru.ivan.eremin.testchat.app.config.AppApi
import ru.ivan.eremin.testchat.app.config.AppConfiguration
import ru.ivan.eremin.testchat.network.constant.UrlConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

    @Provides
    @Singleton
    fun provideConfig(@ApplicationContext context: Context): AppConfiguration {
        return object : AppConfiguration {
            override val context: Context = context
            override val versionCode: Int = BuildConfig.VERSION_CODE
            override val versionName: String = BuildConfig.VERSION_NAME
            override val applicationId: String = BuildConfig.APPLICATION_ID
            override val userAgent: String =
                "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME} (Android ${Build.VERSION.SDK_INT})"

        }
    }

    @Provides
    @Singleton
    fun provideApi(): AppApi {
        return object : AppApi{
            override val baseUrl: String = UrlConstants.BASE_URL
        }
    }
}