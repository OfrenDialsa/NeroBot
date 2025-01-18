package com.nerodev.nerobot.presentation.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface TopBarViewModel {
    val isDropdownExpanded: StateFlow<Boolean>
    val showClearChatDialog: StateFlow<Boolean>
    val showEmptyMessageDialog: StateFlow<Boolean>

    fun toggleDropdown()
    fun dismissDropdown()
    fun showClearChatDialog()
    fun dismissClearChatDialog()
    fun showEmptyMessageDialog()
    fun dismissEmptyMessageDialog()
}