package com.example.myapplication.models

import java.io.Serializable

data class Messages (val id: Int, val content: String, val user: String, val totalComments: Int): Serializable{
    constructor(content: String,user: String,totalComments: Int) : this(-1,content,user,totalComments)

    override fun toString(): String {
        return "User = $user, Messages = $content, Total Comment = $totalComments"
    }
}