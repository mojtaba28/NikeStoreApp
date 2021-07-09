package com.example.nikestore.viewmodel

import com.example.nikestore.common.NikeViewModel
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository):NikeViewModel() {

    val username:String
    get() = userRepository.getUserName()

    val isSignedIn:Boolean
    get() = TokenContainer.token!=null

    fun signOut(){
        userRepository.signOut()
    }
}