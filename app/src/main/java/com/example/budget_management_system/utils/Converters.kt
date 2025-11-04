package com.example.budget_management_system.utils

import androidx.room.TypeConverter
import com.example.budget_management_system.enums.*
import com.example.budget_management_system.models.Money
import com.google.gson.Gson
import java.math.BigDecimal
import java.util.*

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun toUUID(uuid: String?): UUID? = uuid?.let { UUID.fromString(it) }

    @TypeConverter
    fun fromOperationType(type: OperationType?): String? = type?.name

    @TypeConverter
    fun toOperationType(type: String?): OperationType? = type?.let { OperationType.valueOf(it) }

    @TypeConverter
    fun fromPaymentMethod(method: PaymentMethod?): String? = method?.name

    @TypeConverter
    fun toPaymentMethod(method: String?): PaymentMethod? = method?.let { PaymentMethod.valueOf(it) }

    @TypeConverter
    fun fromTagType(type: TagType?): String? = type?.name

    @TypeConverter
    fun toTagType(type: String?): TagType? = type?.let { TagType.valueOf(it) }

    @TypeConverter
    fun fromTagVisibility(visibility: TagVisibility?): String? = visibility?.name

    @TypeConverter
    fun toTagVisibility(visibility: String?): TagVisibility? = visibility?.let { TagVisibility.valueOf(it) }

    @TypeConverter
    fun fromMoney(money: Money?): String? = money?.let { gson.toJson(it) }

    @TypeConverter
    fun toMoney(json: String?): Money? = json?.let { gson.fromJson(it, Money::class.java) }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? = list?.let { gson.toJson(it) }

    @TypeConverter
    fun toStringList(json: String?): List<String>? = json?.let {
        gson.fromJson(it, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromUUIDList(list: List<UUID>?): String? = list?.let { gson.toJson(it) }

    @TypeConverter
    fun toUUIDList(json: String?): List<UUID>? = json?.let {
        gson.fromJson(it, Array<UUID>::class.java).toList()
    }
}
