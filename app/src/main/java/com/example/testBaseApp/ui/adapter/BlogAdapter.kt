package com.example.testBaseApp.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.testBaseApp.base.BaseListAdapter
import com.example.testBaseApp.base.BaseViewHolder
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.LayoutItemBlogBinding

data class Blog(
    val title: String,
)

class BlogAdapter : BaseListAdapter<Blog, LayoutItemBlogBinding, BlogAdapter.BlogViewHolder>(
    diffCallback
) {
    private var onClick: (Int) -> Unit = {}
    fun setOnClickBlog(block: (Int) -> Unit) {
        this.onClick = block
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BlogViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return BlogViewHolder(
            LayoutItemBlogBinding.inflate(inflate, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    inner class BlogViewHolder(override val binding: LayoutItemBlogBinding) :
        BaseViewHolder<Blog, LayoutItemBlogBinding>(binding) {
        override fun onBindData(data: Blog, position: Int): Boolean {
            binding.tvTitle.text = data.title + "$position"
            binding.cardBlog.setOnClickShrinkListener {
                onClick.invoke(position)
            }
            return true
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(
                oldItem: Blog, newItem: Blog
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Blog, newItem: Blog
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}