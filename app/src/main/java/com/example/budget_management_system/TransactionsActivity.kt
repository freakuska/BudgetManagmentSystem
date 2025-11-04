package com.example.budget_management_system

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.financeapp.R

class TransactionsActivity : BaseActivity() {

    private lateinit var listViewTransactions: ListView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        listViewTransactions = findViewById(R.id.listViewTransactions)
        dbHelper = DatabaseHelper(this)

        loadTransactions()
    }

    private fun loadTransactions() {
        val userId = getUserId()
        if (userId == null) {
            Toast.makeText(this, "Ошибка авторизации!", Toast.LENGTH_SHORT).show()
            return
        }

        val transactions = dbHelper.getAllOperations(userId)

        if (transactions.isEmpty()) {
            Toast.makeText(this, "Нет транзакций", Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = TransactionAdapter(this, transactions)
        listViewTransactions.adapter = adapter
    }
}