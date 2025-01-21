package com.nerodev.nerobot.presentation.viewmodel

import android.net.Uri
import com.nerodev.nerobot.domain.model.MessageDomainModel
import kotlinx.coroutines.flow.StateFlow

interface ChatViewModel {
    val messageList: StateFlow<List<MessageDomainModel>>
    val isModelResponding: StateFlow<Boolean>
    val message: StateFlow<String>
    val imageUri: StateFlow<Uri?>
    val errorMessage: StateFlow<String?>
    fun sendMessage(question: String, image: String?)
    fun clearMessages()
    fun skipResponse()
    fun onMessageChange(newMessage: String)
    fun onImageSelected(uri: Uri?, fileName: String?)
    fun clearImage()
    fun setErrorMessage(error: String)
    fun clearErrorMessage()
}