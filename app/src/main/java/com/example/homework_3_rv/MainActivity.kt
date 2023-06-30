package com.example.homework_3_rv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.homework_3_rv.databinding.ActivityMainBinding
import com.github.javafaker.Country
import com.github.javafaker.Faker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val faker = Faker.instance()
    private val countries = (1..30).map { Country(
        id = faker.number().numberBetween(1, 100),
        name = faker.country().name(),
        image = faker.country().flag()
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewCountries.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCountries.adapter = CountriesAdapter(countries)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerViewCountries.addItemDecoration(dividerItemDecoration)

    }
}