package com.example.myapplication.models

import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Repository.MessageRepository

class MessagesViewModel : ViewModel() {
    private val repository = MessageRepository()
    val messageLiveData: LiveData<List<Messages>> = repository.messageLiveData
    val commentsLiveData: LiveData<List<Comments>> = repository.commentsLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    operator fun get(index: Int): Messages? {
        return messageLiveData.value?.get(index)
    }

    fun add(message: Messages) {
        repository.add(message)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
    fun loadcomments(messageId: Int){
        repository.getAllComments(messageId)
    }
    fun deleteComment(messageId: Int, commentId: Int) {
        repository.deleteComments(messageId, commentId)
    }
    fun addcom(messageId: Int, comment: Comments){
        repository.postComment(messageId,comment)
    }
}
