package ru.ivan.eremin.testchat.network.error

import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExceptionsMapper(val value: KClass<out HttpExceptionMapper>)
