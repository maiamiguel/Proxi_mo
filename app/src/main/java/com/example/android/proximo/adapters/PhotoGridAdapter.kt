package com.example.android.proximo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proximo.databinding.GridViewItemBinding
import com.example.android.proximo.models.TypesOfServices

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class PhotoGridAdapter( val onClickListener: OnClickListener) : ListAdapter<TypesOfServices, PhotoGridAdapter.ServiceViewHolder>(DiffCallback) {
    /**
     * The ServiceViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [TypesOfServices] information.
     */
    class ServiceViewHolder(private var binding: GridViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(selectedTypesOfServices: TypesOfServices) {
            binding.property = selectedTypesOfServices
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<TypesOfServices>() {
        override fun areItemsTheSame(oldItem: TypesOfServices, newItem: TypesOfServices): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TypesOfServices, newItem: TypesOfServices): Boolean {
            return oldItem.name == newItem.name
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListener(val clickListener: (selectedTypesOfServices : TypesOfServices) -> Unit) {
        fun onClick(selectedTypesOfServices : TypesOfServices) = clickListener(selectedTypesOfServices)
    }
}