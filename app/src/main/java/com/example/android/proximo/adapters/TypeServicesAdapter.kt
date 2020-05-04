package com.example.android.proximo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proximo.databinding.TypeServiceItemBinding
import com.example.android.proximo.models.Category

class TypeServicesAdapter(private val onClickListener: OnClickListener) : ListAdapter<Category, TypeServicesAdapter.ServiceViewHolder>(DiffCallback) {

    class ServiceViewHolder(private var binding: TypeServiceItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(selectedTypesOfServices: Category) {
            binding.property = selectedTypesOfServices
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.category === newItem.category && oldItem.display === oldItem.display
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.category == newItem.category && oldItem.display == oldItem.display
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(TypeServiceItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (selectedTypesOfServices : Category) -> Unit) {
        fun onClick(selectedTypesOfServices : Category) = clickListener(selectedTypesOfServices)
    }
}
