package com.example.budget_management.models

import com.google.gson.annotations.SerializedName
import com.example.budget_management_system.models.Money

data class CreateOperationDto(
    @SerializedName("type")
    val type: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("notes")
    val notes: String? = null,

    @SerializedName("paymentMethod")
    val paymentMethod: String,

    @SerializedName("operationDateTime")
    val operationDateTime: String,

    @SerializedName("money")
    val money: Money,

    @SerializedName("tagIds")
    val tagIds: List<String> = emptyList()
)
