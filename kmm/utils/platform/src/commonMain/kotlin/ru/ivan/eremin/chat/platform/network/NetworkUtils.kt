package ru.ivan.eremin.chat.platform.network

import kotlinx.coroutines.flow.Flow
import ru.ivan.eremin.chat.platform.network.entity.ConnectType
import ru.ivan.eremin.chat.platform.network.entity.NetworkInterfaces
import ru.ivan.eremin.chat.platform.network.entity.Wlan
import ru.ivan.eremin.chat.platform.network.entity.WlanInterfaces

interface NetworkUtils {
    fun vpnStatusFlow(): Flow<Boolean>
    fun activeNetworkChange(): Flow<Unit>
    fun getTypeConnect(): ConnectType
    fun getNetworkInterfaces(): List<NetworkInterfaces>
    fun getWlanInterfaces(): Flow<WlanInterfaces>
    fun getWlansInterface(): List<Wlan>
    fun getLinkDownstreamBandwidthKbps(): Int?
    fun awaitChangeNetwork()
}