package ru.ivan.eremin.chat.platform.network.entity

data class Wlan(
    val ssid: String,
    val signalQuality: Int,
    val channelNumber: Int,
    val attributes: Attribute
) {
    data class Attribute(
        val bssid: String,
        val capabilities: String,
        val rssi: Int,
    )
}