package com.example.myapplication.models


data class Comments(val id: Int,val messageId: Int, val content: String, val user: String){
    //constructor(comment: String, message: Messages, user: String) : this (-1, comment,-1, user)

    override fun toString(): String {
        return "$user, comment = $content"
    }
}
