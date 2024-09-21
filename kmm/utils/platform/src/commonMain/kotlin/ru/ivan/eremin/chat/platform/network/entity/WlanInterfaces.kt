package ru.ivan.eremin.chat.platform.network.entity

data class WlanInterfaces(
    val signalQuality: Int,
    val wlanInterfaces: List<WlanInterface>
) {
    data class WlanInterface(
        val name: String,
        val attributes: Attributes
    ) {
        data class Attributes(
            val ssid: String,
            val bssid: String,
            val linkSpeed: Int,
            val channel: Int
        )
    }
}
