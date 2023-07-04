package com.example.homework_3_rv.model

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.homework_3_rv.databinding.DialogBinding
import com.github.javafaker.Faker

typealias ContactListener = (contacts: List<Contact>) -> Unit

class ContactService {

    private var contacts = mutableListOf<Contact>()
    private val listeners = mutableSetOf<ContactListener>()

    init {
        val faker = Faker.instance()
        contacts = (1..100).map { Contact(
            id = it,
            name = faker.name().firstName(),
            lastName = faker.name().lastName(),
            phoneNumber = faker.phoneNumber().phoneNumber(),
            checkBox = false
        ) }.toMutableList()
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun addListeners(listener: ContactListener) {
        listeners.add(listener)
        listener.invoke(contacts)
        notifyChanges()
    }

    private fun updateContact(contact: Contact) {
        val oldIndex = contacts.indexOfFirst { it.id == contact.id }
        if (oldIndex != -1) {
            val contact = contacts[oldIndex].copy(
                name = contact.name,
                lastName = contact.lastName,
                phoneNumber = contact.phoneNumber
            )
            contacts = ArrayList(contacts)
            contacts[oldIndex] = contact
        }
        notifyChanges()
    }

    fun setCheck(contact: Contact) {
        val checkedContact = Contact(contact.id, contact.name, contact.lastName, contact.phoneNumber, !contact.checkBox)
        val oldIndex = contacts.indexOfFirst { it.id == checkedContact.id }
        if (oldIndex != -1) {
            contacts = ArrayList(contacts)
            contacts[oldIndex] = checkedContact
            notifyChanges()
        }

    }

    fun deleteContacts() {
        contacts = contacts.filter { !it.checkBox }.toMutableList()
        notifyChanges()
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }

    fun showAddDialog(layoutInflater: LayoutInflater, context: Context) {
        val binding = DialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(context).setView(binding.root)
        val dialog = dialogBuilder.create()

        binding.buttonAddEdit.setOnClickListener {
            val newContact = Contact(
                id = contacts[contacts.size - 1].id + 1,
                name = binding.editTextName.text.toString(),
                lastName = binding.editTextLastName.text.toString(),
                phoneNumber = binding.editTexTPhoneNumber.text.toString(),
                checkBox = false
            )
            contacts.add(newContact)
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showUpdateDialog(layoutInflater: LayoutInflater, context: Context, contact: Contact) {
        val binding = DialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(context).setView(binding.root)
        val dialog = dialogBuilder.create()

        with(binding) {
            editTextName.setText(contact.name)
            editTextLastName.setText(contact.lastName)
            editTexTPhoneNumber.setText(contact.phoneNumber)
            buttonAddEdit.text = "Edit Contact"
        }

        binding.buttonAddEdit.setOnClickListener {
            val updatedContact = Contact(
                id = contact.id,
                name = binding.editTextName.text.toString(),
                lastName = binding.editTextLastName.text.toString(),
                phoneNumber = binding.editTexTPhoneNumber.text.toString(),
                checkBox = false
            )
            updateContact(updatedContact)

            dialog.dismiss()
        }

        dialog.show()
    }

}