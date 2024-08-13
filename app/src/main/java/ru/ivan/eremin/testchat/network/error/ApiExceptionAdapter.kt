package ru.ivan.eremin.testchat.network.error

import javax.inject.Inject

class ApiExceptionAdapter @Inject constructor(apiExceptionMapper: ApiExceptionMapper) :
    ErrorsCallAdapterFactory(apiExceptionMapper)
