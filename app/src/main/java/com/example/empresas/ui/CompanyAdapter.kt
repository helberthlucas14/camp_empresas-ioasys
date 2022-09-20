package com.example.empresas.ui


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.empresas.data.model.Company
import com.example.empresas.databinding.CompanyResultBinding

class CompanyAdapter(
    private val callback: (Company) -> Unit
) : ListAdapter<Company, CompanyAdapter.CompaniesViewHolder>(DIFF_CALLBACK), Filterable {

    private var companies: MutableList<Company> = mutableListOf<Company>()
    private var companiesFiltered: MutableList<Company> = mutableListOf<Company>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesViewHolder {
        return CompaniesViewHolder.create(parent, callback)
    }

    override fun onBindViewHolder(holder: CompaniesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Company>) {
        companies.clear()
        companies.addAll(list)
        submitList(companies)
        notifyDataSetChanged()
    }

    class CompaniesViewHolder(
        private val itemBinding: CompanyResultBinding,
        private val callback: (Company) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(company: Company) {
            itemBinding.run {

                Glide.with(itemView)
                    .load(company.pathImage)
                    .into(imageCompany)

                textCompanyName.text = company.companyName

                textCompanyRole.text = company.companyType?.companyTypeName

                textCompanyCountry.text = company.country
                itemView.setOnClickListener { callback.invoke(company) }
            }
        }


        companion object {
            fun create(parent: ViewGroup, callback: (Company) -> Unit): CompaniesViewHolder {
                val itemBinding = CompanyResultBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return CompaniesViewHolder(itemBinding, callback)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Company>()
                if ((constraint == null) || (constraint.isEmpty())) {
                    if (companies.size > companiesFiltered.size) {
                        companiesFiltered.addAll(companies)
                        filteredList.addAll(companies)
                    } else {
                        filteredList.addAll(companiesFiltered)
                    }
                } else {
                    for (company in companies) {
                        if (company.companyName.startsWith(constraint.toString())) {
                            filteredList.add(company)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                setItems(results?.values as MutableList<Company>)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Company>() {
            override fun areItemsTheSame(
                oldItem: Company,
                newItem: Company
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Company,
                newItem: Company
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}