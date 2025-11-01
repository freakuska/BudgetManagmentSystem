package com.example.budget_management_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : BaseActivity() {

    private lateinit var btnAddTransaction: Button
    private lateinit var btnViewTransactions: Button
    private lateinit var tvBalance: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Проверяем, авторизован ли пользователь
        if (!isLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        btnAddTransaction = findViewById(R.id.btnAddTransaction)
        btnViewTransactions = findViewById(R.id.btnViewTransactions)
        tvBalance = findViewById(R.id.tvBalance)

        btnAddTransaction.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        btnViewTransactions.setOnClickListener {
            val intent = Intent(this, TransactionsActivity::class.java)
            startActivity(intent)
        }

        updateBalance()
    }

    private fun updateBalance() {
        val userId = getUserId()
        if (userId == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val balance = dbHelper.getBalance(userId)
        tvBalance.text = getString(R.string.balance_label, balance)
    }
}