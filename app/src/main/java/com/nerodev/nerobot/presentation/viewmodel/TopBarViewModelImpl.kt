package com.nerodev.nerobot.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TopBarViewModelImpl : ViewModel(), TopBarViewModel {

    private val _isDropdownExpanded = MutableStateFlow(false)
    override val isDropdownExpanded: StateFlow<Boolean> get() = _isDropdownExpanded

    private val _showClearChatDialog = MutableStateFlow(false)
    override val showClearChatDialog: StateFlow<Boolean> get() = _showClearChatDialog

    private val _showEmptyMessageDialog = MutableStateFlow(false)
    override val showEmptyMessageDialog: StateFlow<Boolean> get() = _showEmptyMessageDialog

    override fun toggleDropdown() {
        _isDropdownExpanded.value = !_isDropdownExpanded.value
    }

    override fun dismissDropdown() {
        _isDropdownExpanded.value = false
    }

    override fun showClearChatDialog() {
        _showClearChatDialog.value = true
    }

    override fun dismissClearChatDialog() {
        _showClearChatDialog.value = false
    }

    override fun showEmptyMessageDialog() {
        _showEmptyMessageDialog.value = true
    }

    override fun dismissEmptyMessageDialog() {
        _showEmptyMessageDialog.value = false
    }
}