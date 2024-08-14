package ru.ivan.eremin.testchat.service.phone

import android.content.Context

interface PhoneInfo {

    companion object {
        fun getInstance(context: Context): PhoneInfoImpl {
            return PhoneInfoImpl(context)
        }
    }

    fun getPhoneNumber(): String
}