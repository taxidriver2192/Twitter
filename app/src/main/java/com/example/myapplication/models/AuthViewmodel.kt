package com.example.myapplication.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Repository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewmodel:ViewModel() {
    private val repository: FirebaseRepository = FirebaseRepository()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    val loggedOutData  : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun register(email: String, password: String){
        repository.register(email,password)
        userLiveData.value = repository.userLiveData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }
    fun signIn(email: String, password: String){
        repository.signIn(email,password)
        userLiveData.value = repository.userLiveData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }
    fun signOut(){
        repository.signOut()
        loggedOutData.value = true
    }
}
