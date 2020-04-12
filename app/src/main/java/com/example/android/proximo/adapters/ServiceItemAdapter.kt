package com.example.android.proximo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.proximo.R
import com.example.android.proximo.databinding.ServiceOverviewItemBinding
import com.example.android.proximo.models.Company

class ServiceItemAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<ServiceItemAdapter.ServiceViewHolder?>() {
    private var companies: List<Company> = ArrayList()

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
        val currentCompany: Company = companies.get(i)

        serviceViewHolder.itemView.setOnClickListener {
            onClickListener.onClick(currentCompany)
        }

        serviceViewHolder.serviceListItemBinding.service = currentCompany
    }

    override fun getItemCount(): Int {
        return if (companies.isEmpty()) 0 else companies.size
    }

    fun setServicesList(companies: List<Company>) {
        this.companies = companies
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (selectedTypesOfServices : Company) -> Unit) {
        fun onClick(selectedTypesOfServices : Company) = clickListener(selectedTypesOfServices)
    }

    inner class ServiceViewHolder(serviceListItemBinding: ServiceOverviewItemBinding) : RecyclerView.ViewHolder(serviceListItemBinding.root) {
        internal val serviceListItemBinding: ServiceOverviewItemBinding = serviceListItemBinding
    }
}