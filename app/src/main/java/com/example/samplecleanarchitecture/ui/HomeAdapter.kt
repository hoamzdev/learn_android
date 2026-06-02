package com.example.samplecleanarchitecture.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecleanarchitecture.component.setOnSingleListener
import com.example.samplecleanarchitecture.databinding.ItemExplainerBinding

/**
 * @author hwa..
 */
class HomeAdapter(
    private val items: List<String>,
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var onClick: (String) -> Unit = {}
    fun setOnClickListener(block: (String) -> Unit) {
        this.onClick = block
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int,
    ): HomeViewHolder {
        val binding = ItemExplainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HomeViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class HomeViewHolder(val binding: ItemExplainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                tvTitle.text = item
                cardItem.setOnSingleListener {
                    onClick.invoke(item)
                }
            }
        }
    }
}