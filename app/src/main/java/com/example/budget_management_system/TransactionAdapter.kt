package com.example.budget_management_system

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TransactionAdapter(private val context: Context, private val transactions: List<DatabaseHelper.Operation>) : BaseAdapter() {

    override fun getCount(): Int = transactions.size

    override fun getItem(position: Int): Any = transactions[position]

    override fun getItemId(position: Int): Long = transactions[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val transaction = transactions[position]
        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)

        textView1.text = "${transaction.tagName} - ${transaction.amount} ${transaction.currency}"
        textView2.text = transaction.comment ?: "Без комментария"

        return view
    }
}