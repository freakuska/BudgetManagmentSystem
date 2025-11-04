package com.example.budget_management_system.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budget_management_system.models.dto.TagResponse
import com.example.budget_management_system.models.dto.TagTreeResponse
import com.example.budget_management_system.repositories.TagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TagUiState(
    val tags: List<TagResponse> = emptyList(),
    val tagTree: TagTreeResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTag: TagResponse? = null
)

class TagViewModel(private val tagRepository: TagRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TagUiState())
    val uiState = _uiState.asStateFlow()

    fun loadTags() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = tagRepository.getTags()
            result.onSuccess { tags ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tags = tags
                )
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to load tags"
                )
            }
        }
    }

    fun loadTagsTree() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = tagRepository.getTagsTree()
            result.onSuccess { tree ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tagTree = tree
                )
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to load tags tree"
                )
            }
        }
    }

    fun createTag(name: String, type: String, color: String? = null, icon: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = tagRepository.createTag(name, type, color, icon)
            result.onSuccess { tag ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tags = _uiState.value.tags + tag
                )
                loadTagsTree()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to create tag"
                )
            }
        }
    }

    fun updateTag(id: String, name: String, type: String, color: String? = null, icon: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = tagRepository.updateTag(id, name, type, color, icon)
            result.onSuccess { tag ->
                val updatedList = _uiState.value.tags.map {
                    if (it.id == id) tag else it
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tags = updatedList
                )
                loadTagsTree()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to update tag"
                )
            }
        }
    }

    fun deleteTag(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = tagRepository.deleteTag(id)
            result.onSuccess {
                val updatedList = _uiState.value.tags.filter { it.id != id }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tags = updatedList
                )
                loadTagsTree()
            }
            result.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Failed to delete tag"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
