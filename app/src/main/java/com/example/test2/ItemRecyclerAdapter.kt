package com.example.test2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2.databinding.LayoutAppListBinding
import kotlin.collections.ArrayList


class ItemRecyclerAdapter(private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ItemsViewModel> = ArrayList()
    interface OnItemClickListener {
        fun onItemClick(view: View , item: ItemsViewModel)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogItemHolder(
            LayoutAppListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogItemHolder -> {
                holder.bind2(items[position])
            }
        }
    }
    override fun getItemCount(): Int { return items.size }
    fun submitList(itemList: List<ItemsViewModel>) { items = itemList }

    open class BlogItemHolder
    constructor(
        private val binding: LayoutAppListBinding, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.run { this.listener = listener }
        }
        val itemName = binding.itemTitle
        val itemDate = binding.itemBody

        fun bind2(itemPost: ItemsViewModel) {
            binding.itemPost = itemPost
            itemName.text = itemPost.name
            itemDate.text = itemPost.date
        }
    }
}