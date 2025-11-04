package com.example.budget_management_system.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget_management_system.databinding.ItemTagBinding
import com.example.budget_management_system.models.Tag

class TagAdapter(
    private val onDeleteClick: (Tag) -> Unit = {},
    private val onItemClick: (Tag) -> Unit = {}
) : ListAdapter<Tag, TagAdapter.TagViewHolder>(TagDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding, onDeleteClick, onItemClick)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TagViewHolder(
        private val binding: ItemTagBinding,
        private val onDeleteClick: (Tag) -> Unit,
        private val onItemClick: (Tag) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) {
            binding.apply {
                tagNameTextView.text = tag.name

                try {
                    val color = Color.parseColor(tag.color ?: "#FF6B6B")
                    tagColorView.setBackgroundColor(color)
                } catch (e: Exception) {
                    tagColorView.setBackgroundColor(Color.GRAY)
                }

                deleteButton.setOnClickListener {
                    onDeleteClick(tag)
                }

                root.setOnClickListener {
                    onItemClick(tag)
                }
            }
        }
    }

    private class TagDiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }
}
