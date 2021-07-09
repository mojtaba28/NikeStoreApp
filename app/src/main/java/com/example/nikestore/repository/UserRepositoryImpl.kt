package com.example.nikestore.repository

import com.example.nikestore.common.TokenContainer
import com.example.nikestore.repository.source.UserDataSource
import com.example.nikestore.repository.source.UserLocalDataSource
import com.example.nikestore.repository.source.UserRemoteDataSource
import com.sevenlearn.nikestore.data.TokenResponse
import io.reactivex.rxjava3.core.Completable

class UserRepositoryImpl(val userLocalDataSource: UserDataSource,
                            val userRemoteDataSource: UserDataSource ):UserRepository {
    override fun login(username: String, password: String): Completable {
        return userRemoteDataSource.login(username,password).doOnSuccess(){
            onSuccessfulLogin(username,it)
        }.ignoreElement()
    }

    override fun signUp(username: String, password: String): Completable {
        return userRemoteDataSource.signUp(username, password).ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String {
        return userLocalDataSource.getUsername()
    }

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null,null)
    }

    fun onSuccessfulLogin(username: String,tokenResponse: TokenResponse){

        TokenContainer.update(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveToken(tokenResponse.access_token,tokenResponse.refresh_token)
        userLocalDataSource.saveUsername(username)

    }
}