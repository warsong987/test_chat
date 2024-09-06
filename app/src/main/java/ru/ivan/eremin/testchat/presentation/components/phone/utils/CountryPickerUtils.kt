package ru.ivan.eremin.testchat.presentation.components.phone.utils

import android.content.Context
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import ru.ivan.eremin.testchat.presentation.components.phone.data.CountryDetails

object CountryPickerUtils {
    fun isMobileNumberValid(mobileNumber: String): Boolean {
        return try {
            val phoneNumber = PhoneNumberUtil.getInstance().parse(
                mobileNumber.trim(),
                PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY
            )
            PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)
        } catch (e: NumberParseException) {
            e.printStackTrace()
            false
        }
    }

    fun searchCountryByNumber(context: Context, number: String): CountryDetails? {
        val countries = FunctionHelper.getAllCountries(context)
        val finded = countries.firstOrNull {
            val country = it.countryPhoneNumberCode.filter { it.isDigit() }
            number.startsWith(country)
        }
        return finded
    }
}