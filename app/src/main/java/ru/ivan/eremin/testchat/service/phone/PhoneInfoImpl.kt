package ru.ivan.eremin.testchat.service.phone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.SubscriptionManager.DEFAULT_SUBSCRIPTION_ID
import androidx.annotation.RequiresPermission

class PhoneInfoImpl(private val context: Context) : PhoneInfo {

    private val telephoneManager =
        context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

    @SuppressLint("NewApi")
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS])
    override fun getPhoneNumber(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            telephoneManager.getPhoneNumber(DEFAULT_SUBSCRIPTION_ID)
        else {
            SubscriptionManager.from(context).getPhoneNumber(DEFAULT_SUBSCRIPTION_ID)
        }
    }
}