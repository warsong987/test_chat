package ru.ivan.eremin.testchat.network.timeout

import okhttp3.Request
import retrofit2.Invocation


internal inline fun <reified T> Request.getAnnotation(): T? where T : Annotation {
    return tag(Invocation::class.java)?.method()?.getAnnotation(T::class.java)
}