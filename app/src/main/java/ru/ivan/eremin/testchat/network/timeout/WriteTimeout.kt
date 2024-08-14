package ru.ivan.eremin.testchat.network.timeout

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class WriteTimeout(
    val value: Int,
    val unit: TimeUnit
)
