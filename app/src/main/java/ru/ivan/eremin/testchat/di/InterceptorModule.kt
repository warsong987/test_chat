package ru.ivan.eremin.testchat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Interceptor
import ru.ivan.eremin.testchat.di.qualifierqualifier.interceptor.AppInterceptor
import ru.ivan.eremin.testchat.di.qualifierqualifier.interceptor.NetworkInterceptor
import ru.ivan.eremin.testchat.network.interceptor.DeviceInfoInterceptor
import ru.ivan.eremin.testchat.network.interceptor.RetryInterceptor
import ru.ivan.eremin.testchat.network.interceptor.TimeoutInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal interface InterceptorModule {
    @Multibinds
    @AppInterceptor
    fun interceptor(): Set<Interceptor>

    @Multibinds
    @NetworkInterceptor
    fun networkInterceptor(): Set<Interceptor>

    @AppInterceptor
    @Binds
    @IntoSet
    fun timeOutInterceptor(interceptor: TimeoutInterceptor): Interceptor

    @NetworkInterceptor
    @Binds
    @IntoSet
    fun deviceInfoInterceptor(interceptor: DeviceInfoInterceptor): Interceptor

    @AppInterceptor
    @Binds
    @IntoSet
    fun retryInterceptor(interceptor: RetryInterceptor): Interceptor
}
