package com.example.nikestore.repository.source

import com.example.nikestore.common.CLIENT_ID
import com.example.nikestore.common.CLIENT_SECRET
import com.example.nikestore.services.http.ApiService
import com.google.gson.JsonObject
import com.sevenlearn.nikestore.data.MessageResponse
import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.rxjava3.core.Single

class UserRemoteDataSource(val apiService: ApiService):UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        return apiService.login(JsonObject().apply {
            addProperty("username",username)
            addProperty("password",password)
            addProperty("grant_type","password")
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        })
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        return apiService.signUp(JsonObject().apply {
            addProperty("email",username)
            addProperty("password",password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}