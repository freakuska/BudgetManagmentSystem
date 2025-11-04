package com.example.budget_management_system.models.dto

import com.example.budget_management_system.enums.PaymentMethod
import com.google.gson.annotations.SerializedName
import com.example.budget_management_system.models.Money
import java.util.Date

data class CreateOperationDto(
    val type1: Double,

    @SerializedName("type")
    val type: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("notes")
    val notes: String? = null,

    @SerializedName("paymentMethod")
    val paymentMethod: PaymentMethod,

    @SerializedName("operationDateTime")
    val operationDateTime: Date,

    @SerializedName("money")
    val money: Money,
    @SerializedName("tagIds")
    val tagIds: List<String> = emptyList(),
    val amount: Double
)
