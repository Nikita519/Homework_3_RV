package com.example.homework_3_rv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_3_rv.databinding.ActivityMainBinding
import com.example.homework_3_rv.model.Contact
import com.example.homework_3_rv.model.ContactListener
import com.example.homework_3_rv.model.ContactService
import com.github.javafaker.Faker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactsAdapter
    private val contactService: ContactService
        get() = (applicationContext as App).contactService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsAdapter(object : ActionListener {
            override fun onCheckClicked(contact: Contact) {
                contactService.setCheck(contact)
            }

            override fun onItemClick(position: Int) {
                Log.d("nikita", "onitemclick in mainactivity")
                val contact = adapter.contacts[position]
                contactService.showUpdateDialog(layoutInflater, this@MainActivity, contact)
            }

        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        contactService.addListeners(contactListener)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        val itemTouchHelper = ItemTouchHelper(adapter.itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.buttonAddContact.setOnClickListener {
            contactService.showAddDialog(layoutInflater, this@MainActivity)
        }
        binding.buttonDeleteContacts.setOnClickListener {
            contactService.deleteContacts()
        }
    }

    private val contactListener: ContactListener = {
        adapter.contacts = it
    }
}
