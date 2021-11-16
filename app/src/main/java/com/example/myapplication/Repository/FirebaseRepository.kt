package com.example.myapplication.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val loggedOutData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()

    init {
        userLiveData.value = FirebaseAuth.getInstance().currentUser
    }
    fun register(email: String,password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                userLiveData.value = FirebaseAuth.getInstance().currentUser
                loggedOutData.value = false
            } else {
                errorMessage.value = task.exception?.message
                loggedOutData.value = true
            }
        }
    }
    fun signIn(email: String,password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = FirebaseAuth.getInstance().currentUser
                loggedOutData.value = false
            } else {
                errorMessage.value = task.exception?.message
                loggedOutData.value = true
            }
        }
    }
    fun signOut(){
        auth.signOut()
        loggedOutData.value = true
    }

}
