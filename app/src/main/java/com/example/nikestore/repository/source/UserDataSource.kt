package com.example.nikestore.repository.source

import com.sevenlearn.nikestore.data.MessageResponse
import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.rxjava3.core.Single

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>
    fun signUp(username: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)
    fun saveUsername(username: String)
    fun getUsername(): String
    fun signOut()
}