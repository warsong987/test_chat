package ru.ivan.eremin.chat.platform.network

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.WIFI_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import ru.ivan.eremin.chat.platform.network.entity.ConnectType
import ru.ivan.eremin.chat.platform.network.entity.NetworkInterfaces
import ru.ivan.eremin.chat.platform.network.entity.Wlan
import ru.ivan.eremin.chat.platform.network.entity.WlanInterfaces
import java.math.BigInteger
import java.net.InetAddress

internal class NetworkUtilsImpl(private val context: Context) : NetworkUtils {
    companion object {
        private const val LEVEL = 100
        private val wifiChannels = hashMapOf(
            2412 to 1,
            2417 to 2,
            2422 to 3,
            2427 to 4,
            2432 to 5,
            2437 to 6,
            2442 to 7,
            2447 to 8,
            2452 to 9,
            2457 to 10,
            2462 to 11,
            2467 to 12,
            2472 to 13,
            5180 to 36,
            5200 to 40,
            5220 to 44,
            5240 to 48,
            5260 to 52,
            5280 to 56,
            5300 to 60,
            5320 to 64,
            5500 to 100,
            5520 to 104,
            5540 to 108,
            5560 to 112,
            5580 to 116,
            5600 to 120,
            5620 to 124,
            5640 to 128,
            5660 to 132,
            5680 to 136,
            5700 to 140,
            5745 to 149,
            5765 to 153,
            5785 to 157,
            5805 to 161,
            5825 to 165,
        )
    }

    private val connectivityManager by lazy { context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }
    private val activeNetworkChange by lazy { createNetworkChangeFlow() }
    private val vpnStatusFlow by lazy { createVpnStatusFlow() }

    override fun vpnStatusFlow(): Flow<Boolean> {
        return vpnStatusFlow
    }

    override fun activeNetworkChange(): Flow<Unit> {
        return activeNetworkChange
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    override fun getTypeConnect(): ConnectType {
        return getConnectionTypeMVersion()
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    override fun getNetworkInterfaces(): List<NetworkInterfaces> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getNetworkNewInterfaces()
        } else {
            getNetworkOldInterfaces()
        }
    }


    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getNetworkNewInterfaces(): List<NetworkInterfaces> {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val properties = connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
        return listOf(
            NetworkInterfaces(
                name = "Wireless LAN adapter Wi-Fi",
                ip = properties?.linkAddresses?.firstOrNull {
                    it.address?.hostAddress?.contains(".") == true
                }?.address?.hostAddress,
                gateway = properties?.dhcpServerAddress?.hostAddress ?: ""
            )
        )
    }

    @Suppress("deprecation")
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    private fun getNetworkOldInterfaces(): List<NetworkInterfaces> {
        val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return listOf(
            NetworkInterfaces(
                name = "Wireless LAN adapter Wi-Fi",
                ip = InetAddress.getByAddress(
                    BigInteger.valueOf(wifiManager.connectionInfo.ipAddress.toLong()).toByteArray()
                ).hostAddress,
                gateway = InetAddress.getByAddress(
                    BigInteger.valueOf(wifiManager.dhcpInfo.gateway.toLong()).toByteArray()
                ).hostAddress ?: ""
            )
        )
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    override fun getWlanInterfaces(): Flow<WlanInterfaces> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWlanInterfaceSSdk()
        } else {
            flowOf(getWlanInterfaceQSdk())
        }
    }

    @Suppress("deprecation")
    fun getWlanInterfaceQSdk(): WlanInterfaces {
        val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return WlanInterfaces(
            signalQuality = WifiManager.compareSignalLevel(wifiManager.connectionInfo.rssi, LEVEL),
            wlanInterfaces = listOf(
                WlanInterfaces.WlanInterface(
                    name = "Wi-Fi",
                    attributes = WlanInterfaces.WlanInterface.Attributes(
                        ssid = wifiManager.connectionInfo.ssid,
                        bssid = wifiManager.connectionInfo.bssid,
                        linkSpeed = wifiManager.connectionInfo.linkSpeed,
                        channel = wifiChannels[wifiManager.connectionInfo.frequency] ?: 0
                    )
                )
            )
        )
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    @RequiresApi(Build.VERSION_CODES.S)
    fun getWlanInterfaceSSdk(): Flow<WlanInterfaces> {
        return callbackFlow {
            val request =
                NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()
            val networkCallback = object : NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    (networkCapabilities.transportInfo as? WifiInfo)?.let {
                        if (it.supplicantState == SupplicantState.COMPLETED) {
                            val wifiManager =
                                context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
                            trySendBlocking(
                                WlanInterfaces(
                                    signalQuality = wifiManager.calculateSignalLevel(it.rssi),
                                    wlanInterfaces = listOf(
                                        WlanInterfaces.WlanInterface(
                                            name = "Wi-Fi",
                                            attributes = WlanInterfaces.WlanInterface.Attributes(
                                                ssid = it.ssid,
                                                bssid = it.bssid,
                                                linkSpeed = it.linkSpeed,
                                                channel = wifiChannels[it.frequency] ?: 0
                                            )
                                        )
                                    )
                                )

                            ).onFailure { cause ->
                                close(cause)
                            }.onSuccess {
                                close()
                            }
                        }
                    } ?: close()
                }
            }

            connectivityManager.requestNetwork(request, networkCallback)
            connectivityManager.registerNetworkCallback(request, networkCallback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }
    }

    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
        ]
    )
    override fun getWlansInterface(): List<Wlan> {
        val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return wifiManager.scanResults.map {
            Wlan(
                ssid = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    getWifiNewSSID(it)
                } else {
                    getWifiOldSSID(it)
                },
                signalQuality = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    getSignalNewQuality(it.level)
                } else {
                    getSignalOldQuality(it.level)
                },
                channelNumber = wifiChannels[it.frequency] ?: 0,
                attributes = Wlan.Attribute(
                    bssid = it.BSSID,
                    capabilities = it.capabilities,
                    rssi = it.level
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getWifiNewSSID(scanResult: ScanResult): String {
        return scanResult.wifiSsid.toString()
    }

    @Suppress("deprecation")
    private fun getWifiOldSSID(scanResult: ScanResult): String {
        return scanResult.SSID
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getSignalNewQuality(level: Int): Int {
        val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return wifiManager.calculateSignalLevel(level)
    }

    private fun getSignalOldQuality(level: Int): Int {
        return WifiManager.compareSignalLevel(level, LEVEL)
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    override fun getLinkDownstreamBandwidthKbps(): Int? {
        return connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)?.linkDownstreamBandwidthKbps
        }
    }

    override fun awaitChangeNetwork() {
        merge(
            activeNetworkChange(),
            vpnStatusFlow().drop(1).map {  }
        ).flowOn(Dispatchers.IO)
    }

    private fun createNetworkChangeFlow(): SharedFlow<Unit> {
        return callbackFlow {
            val callback = ConnectivityManager.OnNetworkActiveListener {
                channel.trySend(Unit)
            }
            connectivityManager.addDefaultNetworkActiveListener(callback)
            awaitClose {
                try {
                    connectivityManager.removeDefaultNetworkActiveListener(callback)
                } catch (_: Exception) {
                    // no-op
                }
            }
        }.shareIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed()
        )
    }

    @SuppressLint("MissingPermission")
    private fun createVpnStatusFlow(): SharedFlow<Boolean> {
        return callbackFlow {
            val callback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    channel.trySend(true)
                }

                override fun onLost(network: Network) {
                    channel.trySend(false)
                }
            }
            connectivityManager.registerNetworkCallback(
                NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
                    .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
                    .build(),
                callback
            )
            awaitClose {
                try {
                    connectivityManager.unregisterNetworkCallback(callback)
                } catch (_: Exception) {
                    // no-op
                }
            }
        }.shareIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(),
        )
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_NETWORK_STATE])
    private fun getConnectionTypeMVersion(): ConnectType {
        return connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)?.let { capabilities ->
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectType.WIFI
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectType.MOBILE
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectType.ETHERNET
                    else -> ConnectType.UNKNOWN
                }
            } ?: ConnectType.UNKNOWN
        } ?: ConnectType.UNKNOWN
    }
}