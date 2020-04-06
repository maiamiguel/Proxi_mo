package com.example.android.proximo.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proximo.databinding.GridViewItemBinding
import com.example.android.proximo.databinding.ServiceOverviewItemBinding
import com.example.android.proximo.models.Service
import com.example.android.proximo.models.TypesOfServices

class ServiceItemAdapter ( val onClickListener: OnClickListener): ListAdapter<Service, ServiceItemAdapter.ServiceViewHolder>(DiffCallback) {

    class ServiceViewHolder(private var binding: ServiceOverviewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {
            binding.service = service
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(ServiceOverviewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(service)
        }
        holder.bind(service)
    }

    class OnClickListener(val clickListener: (selectedTypesOfServices : Service) -> Unit) {
        fun onClick(selectedTypesOfServices : Service) = clickListener(selectedTypesOfServices)
    }
}