package ru.ivan.eremin.chat.network

import exception.ConnectionException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.ivan.eremin.chat.platform.network.NetworkUtils
import ru.ivan.eremin.chat.utils.kotlin.ResultFlow


class RequestHelper(
    private val scope: CoroutineScope,
    private val networkUtils: NetworkUtils?,
) {

    private val mutex = Mutex()
    private val mutexes = mutableMapOf<String, Mutex>()


    fun <T> getCacheOrNetwork(
        cache: MutableStateFlow<T?>,
        key: String = DEFAULT_KEY,
        network: suspend () -> T
    ): Flow<ResultFlow<T>> {
        return channelFlow {
            cache.collectLatest {
                it?.let { send(ResultFlow.Success(it)) }
                if (it == null) {
                    runRequest(key, cache, network)
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun <T> ProducerScope<ResultFlow<T>>.runRequest(
        key: String,
        cache: MutableStateFlow<T?>,
        network: suspend () -> T
    ) {
        send(ResultFlow.Loading)
        val result = scope.async(SupervisorJob()) {
            mutex.withLock { mutexes.getOrPut(key) { Mutex() } }.withLock {
                cache.value ?: withContext(Dispatchers.IO) {
                    val response = network.invoke()
                    cache.emit(response)
                }
            }
        }

        try {
            result.await()
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Throwable) {
            if (!isClosedForSend) send(ResultFlow.ErrorResult(e))
            if (e is ConnectionException && networkUtils != null) {
                networkUtils.awaitChangeNetwork()
                // если сеть изменилась - пытаемся повторить запрос
                runRequest(key, cache, network)
            }
        }
    }

    companion object {
        private const val DEFAULT_KEY = "KEY"
    }
}