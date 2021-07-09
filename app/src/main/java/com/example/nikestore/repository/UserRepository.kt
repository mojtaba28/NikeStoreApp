package com.example.nikestore.repository

import io.reactivex.rxjava3.core.Completable

interface UserRepository {

    fun login(username:String,password:String):Completable
    fun signUp(username:String,password:String):Completable
    fun loadToken()
    fun getUserName():String
    fun signOut()
}