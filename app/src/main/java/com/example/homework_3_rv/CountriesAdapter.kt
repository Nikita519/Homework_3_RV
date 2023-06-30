package com.example.homework_3_rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_3_rv.databinding.CountryItemBinding

class CountriesAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    class CountriesViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryItemBinding.inflate(inflater, parent, false)
        return CountriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countries[position]
        with(holder.binding) {
            textViewName.text = country.name
            Glide.with(imageViewFlag.context)
                .load(country.image)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageViewFlag)
            textViewId.text = country.id.toString()
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}