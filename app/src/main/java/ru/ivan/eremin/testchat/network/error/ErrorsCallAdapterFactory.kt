package ru.ivan.eremin.testchat.network.error

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Invocation
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class ErrorsCallAdapterFactory(private val defaultMapper: HttpExceptionMapper? = null) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, Call<*>>? {
        if (getRawType(returnType) != Call::class.java ||
            returnType !is ParameterizedType ||
            returnType.actualTypeArguments.size != 1
        ) {
            return null
        }

        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        @Suppress("UNCHECKED_CAST")
        return ErrorsCallAdapter(
            delegateAdapter = delegate as CallAdapter<Any, Call<*>>,
            defaultMapper
        )
    }
}

class ErrorsCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>,
    private val defaultMapper: HttpExceptionMapper? = null
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>): Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call, defaultMapper))
    }
}

class CallWithErrorHandling(
    private val delegate: Call<Any>,
    private val defaultMapper: HttpExceptionMapper? = null
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(call, mapExceptionOfCall(call, HttpException(response)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapExceptionOfCall(call, t))
            }
        })
    }

    override fun clone() = CallWithErrorHandling(delegate.clone())

    fun mapExceptionOfCall(call: Call<Any>, t: Throwable): Throwable {
        val retrofitInvocation = call.request().tag(Invocation::class.java)
        val annotation = retrofitInvocation?.method()?.getAnnotation(ExceptionsMapper::class.java)
        val mapper = try {
            annotation?.value?.java?.constructors?.first()
                ?.newInstance(retrofitInvocation.arguments()) as HttpExceptionMapper
        } catch (_: Exception) {
            null
        } ?: defaultMapper
        return mapToDomainException(t, mapper)
    }
}

fun mapToDomainException(
    remoteException: Throwable,
    httpExceptionsMapper: HttpExceptionMapper? = null
): Throwable {
    return when {
        remoteException is HttpException && httpExceptionsMapper != null -> {
            httpExceptionsMapper.map(remoteException) ?: remoteException
        }

        else -> remoteException
    }
}
