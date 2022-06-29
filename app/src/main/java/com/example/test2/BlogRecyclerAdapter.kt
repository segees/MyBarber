package com.example.test2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.test2.databinding.LayoutBlogListItemBinding
import kotlin.collections.ArrayList

class BlogRecyclerAdapter (private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<BlogPost> = ArrayList()
    interface OnItemClickListener {
        fun onItemClick(view: View , item: BlogPost)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutBlogListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogViewHolder -> {
                holder.bind(items[position])
            }
        }
    }
    override fun getItemCount(): Int { return items.size }
    fun submitList(blogList: List<BlogPost>) { items = blogList }

    open class BlogViewHolder
    constructor(
        private val binding: LayoutBlogListItemBinding, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.run { this.listener = listener }
        }
        val blogImage = binding.blogImage
        val blogTitle = binding.blogTitle
        val blogBody = binding.blogBody

        fun bind(blogPost: BlogPost) {
            binding.blogPost = blogPost
            blogTitle.text = blogPost.title
            blogBody.text = blogPost.body
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)
            Glide.with(blogBody.context)
                .setDefaultRequestOptions(requestOptions)
                .load(blogPost.image)
                .into(blogImage)
        }
    }
}