package com.example.budget_management_system.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget_management_system.databinding.ItemTransactionBinding
import com.example.budget_management_system.models.FinancialOperation
import com.example.budget_management_system.utils.CurrencyUtils
import com.example.budget_management_system.utils.DateUtils

class TransactionAdapter(
    private val onDeleteClick: (FinancialOperation) -> Unit = {},
    private val onEditClick: (FinancialOperation) -> Unit = {}
) : ListAdapter<FinancialOperation, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding, onDeleteClick, onEditClick)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TransactionViewHolder(
        private val binding: ItemTransactionBinding,
        private val onDeleteClick: (FinancialOperation) -> Unit,
        private val onEditClick: (FinancialOperation) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(operation: FinancialOperation) {
            binding.apply {
                transactionDescriptionTextView.text = operation.description
                transactionDateTextView.text = DateUtils.formatToDisplay(operation.date)

                val amount = CurrencyUtils.formatCurrency(operation.amount)
                transactionAmountTextView.text = amount

                val color = when (operation.type) {
                    "Income" -> Color.GREEN
                    "Expense" -> Color.RED
                    "Transfer" -> Color.BLUE
                    else -> Color.GRAY
                }
                transactionAmountTextView.setTextColor(color)

                transactionTypeTextView.text = operation.type

                deleteButton.setOnClickListener {
                    onDeleteClick(operation)
                }

                editButton.setOnClickListener {
                    onEditClick(operation)
                }

                root.setOnClickListener {
                    onEditClick(operation)
                }
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<FinancialOperation>() {
        override fun areItemsTheSame(oldItem: FinancialOperation, newItem: FinancialOperation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FinancialOperation, newItem: FinancialOperation): Boolean {
            return oldItem == newItem
        }
    }
}
