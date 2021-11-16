package com.example.myapplication.Repository


import com.example.myapplication.models.Comments
import retrofit2.Call
import retrofit2.http.*
import com.example.myapplication.models.Messages


interface MessageService {
    @GET("messages")
    fun getAllMessages(): Call<List<Messages>>
    @GET("messages/{messageId}/comments")
    fun getComments(@Path("messageId") MessageId: Int): Call<List<Comments>>

    @POST("messages/{messageId}/comments")
    fun postComment(@Path("messageId")MessageId: Int, @Body comment: Comments): Call <Comments>

    @POST("messages")
    fun saveMessage(@Body message: Messages): Call<Messages>

    @DELETE("messages/{id}")
    fun deleteMessage(@Path("id") id: Int): Call<Messages>

    @DELETE("messages/{messageId}/comments/{commentId}")
    fun deleteComment(@Path("messageId") MessageId: Int, @Path("commentId") commentId: Int ): Call <Comments>
}