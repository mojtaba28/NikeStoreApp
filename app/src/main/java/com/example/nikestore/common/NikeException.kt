package com.example.nikestore.common

import androidx.annotation.StringRes

class NikeException(val type:Type,@StringRes val clientMessage: Int = 0
                        ,val backendMessage:String?=null):Throwable() {



    enum class Type{
        SIMPLE, DIALOG, AUTH
    }
}