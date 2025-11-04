package com.example.budget_management_system

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.financeapp.R

class AddTransactionActivity : BaseActivity() {

    private lateinit var editAmount: EditText
    private lateinit var radioGroupType: RadioGroup
    private lateinit var radioIncome: RadioButton
    private lateinit var radioExpense: RadioButton
    private lateinit var spinnerCategory: Spinner
    private lateinit var editComment: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        editAmount = findViewById(R.id.editAmount)
        radioGroupType = findViewById(R.id.radioGroupType)
        radioIncome = findViewById(R.id.radioIncome)
        radioExpense = findViewById(R.id.radioExpense)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        editComment = findViewById(R.id.editComment)
        btnSave = findViewById(R.id.btnSave)
        dbHelper = DatabaseHelper(this)

        loadCategories()

        btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun loadCategories() {
        val tags = dbHelper.getAllTags()
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            tags.map { it.name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    private fun saveTransaction() {
        val amountStr = editAmount.text.toString().trim()
        val comment = editComment.text.toString().trim()

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Введите сумму!", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = try {
            amountStr.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Неверный формат суммы!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedId = radioGroupType.checkedRadioButtonId
        val type = when (selectedId) {
            R.id.radioIncome -> "income"
            R.id.radioExpense -> "expense"
            else -> ""
        }

        if (type.isEmpty()) {
            Toast.makeText(this, "Выберите тип транзакции!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedTagPosition = spinnerCategory.selectedItemPosition
        val selectedTag = dbHelper.getAllTags()[selectedTagPosition]

        val userId = getUserId()
        if (userId == null) {
            Toast.makeText(this, "Ошибка авторизации!", Toast.LENGTH_SHORT).show()
            return
        }

        val id = dbHelper.addOperation(
            userId = userId,
            amount = amount,
            tagId = selectedTag.id,
            paymentMethod = "Карта",
            currency = "RUB",
            comment = comment
        )

        if (id > 0) {
            Toast.makeText(this, "Транзакция сохранена!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Ошибка сохранения!", Toast.LENGTH_SHORT).show()
        }
    }
}