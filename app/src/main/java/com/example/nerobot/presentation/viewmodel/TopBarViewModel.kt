package com.example.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopBarViewModel : ViewModel() {

    private val _isDropdownExpanded = MutableStateFlow(false)
    val isDropdownExpanded: StateFlow<Boolean> get() = _isDropdownExpanded

    private val _showClearChatDialog = MutableStateFlow(false)
    val showClearChatDialog: StateFlow<Boolean> get() = _showClearChatDialog

    private val _showEmptyMessageDialog = MutableStateFlow(false)
    val showEmptyMessageDialog: StateFlow<Boolean> get() = _showEmptyMessageDialog

    fun toggleDropdown() {
        _isDropdownExpanded.value = !_isDropdownExpanded.value
    }

    fun dismissDropdown() {
        _isDropdownExpanded.value = false
    }

    fun showClearChatDialog() {
        _showClearChatDialog.value = true
    }

    fun dismissClearChatDialog() {
        _showClearChatDialog.value = false
    }

    fun showEmptyMessageDialog() {
        _showEmptyMessageDialog.value = true
    }

    fun dismissEmptyMessageDialog() {
        _showEmptyMessageDialog.value = false
    }
}