package com.example.android.proximo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proximo.R
import com.example.android.proximo.databinding.ServiceOverviewItemBinding
import com.example.android.proximo.models.Service

class ServiceItemAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<ServiceItemAdapter.ServiceViewHolder?>() {
    private var services: List<Service> = ArrayList()

    override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            i: Int
    ): ServiceViewHolder {
        val tweetListItemBinding: ServiceOverviewItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.service_overview_item, viewGroup, false
        )
        return ServiceViewHolder(tweetListItemBinding)
    }

    override fun onBindViewHolder(
            serviceViewHolder: ServiceViewHolder,
            i: Int
    ) {
        val currentService: Service = services.get(i)

        serviceViewHolder.itemView.setOnClickListener {
            onClickListener.onClick(currentService)
        }

        serviceViewHolder.serviceListItemBinding.service = currentService
    }

    override fun getItemCount(): Int {
        return if (services.isEmpty()) 0 else services.size
    }

    fun setServicesList(services: List<Service>) {
        this.services = services
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (selectedTypesOfServices : Service) -> Unit) {
        fun onClick(selectedTypesOfServices : Service) = clickListener(selectedTypesOfServices)
    }

    inner class ServiceViewHolder(serviceListItemBinding: ServiceOverviewItemBinding) : RecyclerView.ViewHolder(serviceListItemBinding.root) {
        internal val serviceListItemBinding: ServiceOverviewItemBinding = serviceListItemBinding
    }
}