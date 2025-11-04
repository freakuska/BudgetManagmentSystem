package com.example.budget_management_system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.budget_management_system.models.dto.TagResponse
import com.example.budget_management_system.viewmodels.TagViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(tagViewModel: TagViewModel) {
    val state by tagViewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var newTagName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Expense") }

    LaunchedEffect(Unit) {
        tagViewModel.loadTagsTree()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tags",
                style = MaterialTheme.typography.headlineLarge
            )

            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Tag")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                if (state.tagTree != null) {
                    TagSection(
                        title = "Income",
                        tags = state.tagTree!!.income,
                        onDeleteTag = { tagViewModel.deleteTag(it) }
                    )
                }
            }

            item {
                if (state.tagTree != null) {
                    TagSection(
                        title = "Expense",
                        tags = state.tagTree!!.expense,
                        onDeleteTag = { tagViewModel.deleteTag(it) }
                    )
                }
            }

            item {
                if (state.tagTree != null) {
                    TagSection(
                        title = "Transfer",
                        tags = state.tagTree!!.transfer,
                        onDeleteTag = { tagViewModel.deleteTag(it) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Tag") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newTagName,
                        onValueChange = { newTagName = it },
                        label = { Text("Tag Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Income", "Expense", "Transfer").forEach { type ->
                            FilterChip(
                                selected = selectedType == type,
                                onClick = { selectedType = type },
                                label = { Text(type) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTagName.isNotEmpty()) {
                            tagViewModel.createTag(newTagName, selectedType)
                            newTagName = ""
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TagSection(
    title: String,
    tags: List<TagResponse>,
    onDeleteTag: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        tags.forEach { tag ->
            TagItem(tag = tag, onDelete = { onDeleteTag(tag.id) })
        }
    }
}

@Composable
fun TagItem(tag: TagResponse, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = try {
                                Color(android.graphics.Color.parseColor(tag.color ?: "#FF6B6B"))
                            } catch (e: Exception) {
                                Color.Gray
                            },
                            shape = CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
