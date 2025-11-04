package com.example.budget_management_system.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LogoutOutlined
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budget_management_system.models.dto.OperationResponse
import com.example.budget_management_system.models.dto.TagResponse
import com.example.budget_management_system.viewmodels.OperationViewModel
import com.example.budget_management_system.viewmodels.AuthViewModel
import com.example.budget_management_system.viewmodels.TagViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    operationViewModel: OperationViewModel,
    tagViewModel: TagViewModel,
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val operationState by operationViewModel.uiState.collectAsState()
    val tagState by tagViewModel.uiState.collectAsState()

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf<TagResponse?>(null) }
    var selectedType by remember { mutableStateOf("Expense") }

    LaunchedEffect(Unit) {
        operationViewModel.loadOperations()
        operationViewModel.loadStats()
        tagViewModel.loadTags()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineLarge
            )

            IconButton(onClick = {
                authViewModel.logout()
                onLogout()
            }) {
                Icon(Icons.Default.LogoutOutlined, contentDescription = "Logout")
            }
        }

        // Stats
        if (operationState.stats != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    label = "Income",
                    amount = operationState.stats!!.totalIncome,
                    backgroundColor = Color.Green.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Expense",
                    amount = operationState.stats!!.totalExpense,
                    backgroundColor = Color.Red.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Balance",
                    amount = operationState.stats!!.balance,
                    backgroundColor = Color.Blue.copy(alpha = 0.1f),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Add Operation Form
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add Operation", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                // Type Selection
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
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

                // Tag Selection
                if (tagState.tags.isNotEmpty()) {
                    var expandedTag by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expandedTag,
                        onExpandedChange = { expandedTag = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        OutlinedTextField(
                            value = selectedTag?.name ?: "Select Tag",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tag") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTag,
                            onDismissRequest = { expandedTag = false }
                        ) {
                            tagState.tags.forEach { tag ->
                                DropdownMenuItem(
                                    text = { Text(tag.name) },
                                    onClick = {
                                        selectedTag = tag
                                        expandedTag = false
                                    }
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && selectedTag != null) {
                            operationViewModel.createOperation(
                                amount = amount.toDouble(),
                                description = description,
                                type = selectedType,
                                tagId = selectedTag!!.id,
                                date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                            )
                            amount = ""
                            description = ""
                            selectedTag = null
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text("Add")
                }
            }
        }

        // Operations List
        Text(
            text = "Recent Operations",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(operationState.operations) { operation ->
                OperationItem(
                    operation = operation,
                    onDelete = { operationViewModel.deleteOperation(operation.id) }
                )
            }
        }
    }
}

@Composable
fun StatCard(
    label: String,
    amount: Double,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(
                text = "%.2f".format(amount),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun OperationItem(
    operation: OperationResponse,
    onDelete: () -> Unit
) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = operation.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = operation.tag?.name ?: "Unknown",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "%.2f".format(operation.amount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (operation.type) {
                        "Income" -> Color.Green
                        "Expense" -> Color.Red
                        else -> Color.Gray
                    }
                )

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
}
