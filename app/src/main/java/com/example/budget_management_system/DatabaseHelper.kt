package com.example.budget_management_system

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "financeapp.db"
        private const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE users (" +
                    "id TEXT PRIMARY KEY, " +
                    "login TEXT NOT NULL UNIQUE, " +
                    "password_hash TEXT NOT NULL, " +
                    "full_name TEXT, " +
                    "is_active INTEGER DEFAULT 1, " +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")"
        )

        db.execSQL(
            "CREATE TABLE tags (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL UNIQUE, " +
                    "type TEXT CHECK(type IN ('income', 'expense'))" +
                    ")"
        )

        db.execSQL(
            "CREATE TABLE operations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id TEXT NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "tag_id INTEGER NOT NULL, " +
                    "payment_method TEXT, " +
                    "currency TEXT DEFAULT 'RUB', " +
                    "comment TEXT, " +
                    "operation_datetime DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE SET NULL" +
                    ")"
        )

        Log.d("DB", "База данных создана.")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS operations")
        db.execSQL("DROP TABLE IF EXISTS tags")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(userId: String, login: String, passwordHash: String, fullName: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("id", userId)
            put("login", login)
            put("password_hash", passwordHash)
            if (!fullName.isNullOrEmpty()) put("full_name", fullName)
        }
        return db.insert("users", null, values)
    }

    fun getUser(login: String, passwordHash: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE login = ? AND password_hash = ?"
        val cursor = db.rawQuery(query, arrayOf(login, passwordHash))

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                login = cursor.getString(cursor.getColumnIndexOrThrow("login")),
                passwordHash = cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow("full_name")),
                isActive = cursor.getInt(cursor.getColumnIndexOrThrow("is_active")) == 1
            )
        }

        cursor.close()
        db.close()
        return user
    }

    fun addTag(name: String, type: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("type", type)
        }
        return db.insert("tags", null, values)
    }

    fun getAllTags(): List<Tag> {
        val tags = mutableListOf<Tag>()
        val query = "SELECT * FROM tags ORDER BY name ASC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val tag = Tag(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                )
                tags.add(tag)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return tags
    }

    fun addOperation(userId: String, amount: Double, tagId: Long, paymentMethod: String?, currency: String, comment: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("amount", amount)
            put("tag_id", tagId)
            put("payment_method", paymentMethod)
            put("currency", currency)
            put("comment", comment)
        }
        return db.insert("operations", null, values)
    }

    fun getAllOperations(userId: String): List<Operation> {
        val operations = mutableListOf<Operation>()
        val query = """
            SELECT o.id, o.user_id, o.amount, o.tag_id, t.name AS tag_name, 
                   o.payment_method, o.currency, o.comment, o.operation_datetime
            FROM operations o
            INNER JOIN tags t ON o.tag_id = t.id
            WHERE o.user_id = ?
            ORDER BY o.operation_datetime DESC
        """.trimIndent()
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(userId))

        if (cursor.moveToFirst()) {
            do {
                val operation = Operation(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    userId = cursor.getString(cursor.getColumnIndexOrThrow("user_id")),
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                    tagId = cursor.getLong(cursor.getColumnIndexOrThrow("tag_id")),
                    tagName = cursor.getString(cursor.getColumnIndexOrThrow("tag_name")),
                    paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow("payment_method")),
                    currency = cursor.getString(cursor.getColumnIndexOrThrow("currency")),
                    comment = cursor.getString(cursor.getColumnIndexOrThrow("comment")),
                    operationDatetime = cursor.getString(cursor.getColumnIndexOrThrow("operation_datetime"))
                )
                operations.add(operation)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return operations
    }

    fun getBalance(userId: String): Double {
        val query = "SELECT SUM(CASE WHEN type = 'income' THEN amount ELSE -amount END) FROM operations o INNER JOIN tags t ON o.tag_id = t.id WHERE o.user_id = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, arrayOf(userId))

        var balance = 0.0
        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(0)
        }

        cursor.close()
        db.close()
        return balance
    }

    data class User(
        val id: String,
        val login: String,
        val passwordHash: String,
        val fullName: String?,
        val isActive: Boolean
    )

    data class Tag(
        val id: Long,
        val name: String,
        val type: String
    )

    data class Operation(
        val id: Long,
        val userId: String,
        val amount: Double,
        val tagId: Long,
        val tagName: String,
        val paymentMethod: String?,
        val currency: String,
        val comment: String?,
        val operationDatetime: String
    )
}