package com.example.myapplication.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.models.Comments
import com.example.myapplication.models.Messages
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageRepository {
    private val url = "https://anbo-restmessages.azurewebsites.net/api/"


    private val messageService: MessageService
    val messageLiveData: MutableLiveData<List<Messages>> = MutableLiveData<List<Messages>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val commentsLiveData: MutableLiveData<List<Comments>> = MutableLiveData<List<Comments>>()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        messageService = build.create(MessageService::class.java)
        getPosts()
    }

    fun getPosts() {
        messageService.getAllMessages().enqueue(object : Callback<List<Messages>> {
            override fun onResponse(
                call: Call<List<Messages>>,
                response: Response<List<Messages>>
            ) {
                if (response.isSuccessful) {
                    messageLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("ERROR", message)
                }
            }

            override fun onFailure(call: Call<List<Messages>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("ERROR", t.message!!)
            }
        })
    }

    fun add(message: Messages) {
        messageService.saveMessage(message).enqueue(object : Callback<Messages> {
            override fun onResponse(call: Call<Messages>, response: Response<Messages>) {
                if (response.isSuccessful) {
                    Log.d("ERROR", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("ERROR", message)
                }
            }

            override fun onFailure(call: Call<Messages>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("ERROR", t.message!!)
            }
        })
    }

    fun delete(id: Int) {
        messageService.deleteMessage(id).enqueue(object : Callback<Messages> {
            override fun onResponse(call: Call<Messages>, response: Response<Messages>) {
                if (response.isSuccessful) {
                    Log.d("ERROR", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("ERROR", message)
                }
            }

            override fun onFailure(call: Call<Messages>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("ERROR", t.message!!)
            }
        })
    }

    fun deleteComments(messageId: Int, commentId: Int) {
        messageService.deleteComment(messageId, commentId).enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                if (response.isSuccessful) {
                    updateMessageLiveData.postValue("Deleted: " + response.message())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)

                }
            }


            override fun onFailure(call: Call<Comments>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)


            }

        })
    }

    fun getAllComments(MessageId: Int) {
        messageService.getComments(MessageId).enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
                    commentsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("ERROR", message)
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("ERROR", t.message!!)
            }
        })
    }

    fun postComment(messageId: Int, comment: Comments) {
        messageService.postComment(messageId, comment).enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                if (response.isSuccessful) {
                    updateMessageLiveData.postValue("Deleted: " + " " + response.message())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }
}