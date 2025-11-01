package com.example.budget_management_system.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget_management_system.models.database.TagEntity
import com.example.budget_management_system.models.dto.TagDto
import com.example.budget_management_system.repositories.TagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class TagViewModel(private val repository: TagRepository) : ViewModel() {
    private val _tags = MutableStateFlow<List<TagEntity>>(emptyList())
    val tags: StateFlow<List<TagEntity>> = _tags.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadTags()
    }

    fun loadTags() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.fetchTagsFromServer()
                repository.getAllTags().collect { tags ->
                    _tags.value = tags
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createTag(tag: TagDto) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.createTag(tag)
                loadTags()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTag(id: UUID) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteTag(id)
                loadTags()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
