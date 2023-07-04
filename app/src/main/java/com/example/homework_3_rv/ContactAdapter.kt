package com.example.homework_3_rv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_3_rv.databinding.ContactItemBinding
import com.example.homework_3_rv.model.Contact
import com.example.homework_3_rv.model.ContactService
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.util.Collections

interface ActionListener {

    fun onCheckClicked(contact: Contact)

    fun onItemClick(position: Int)
}

class ContactsAdapter(
    private val actionListener: ActionListener
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), View.OnClickListener {

    class ContactsViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root)


    var contacts: List<Contact> = emptyList()
        set(newValue) {
            val diffCallback = ContactsDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
            //notifyDataSetChanged()
        }



    val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            var dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = 0

            return makeMovementFlags(dragFlags, swipeFlags)
        }


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.bindingAdapterPosition
            val toPosition= target.bindingAdapterPosition

            Collections.swap(contacts, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

    }




    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsAdapter.ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            textViewID.text = contact.id.toString()
            textViewName.text = contact.name
            textViewLastName.text = contact.lastName
            textViewPhoneNumber.text = contact.phoneNumber
            checkBoxDelete.isChecked = contact.checkBox
        }
        holder.itemView.setOnClickListener {
            actionListener.onItemClick(position)
        }
        holder.binding.checkBoxDelete.setOnClickListener {
            actionListener.onCheckClicked(contact)
        }

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onClick(v: View?) {

    }



}

class ContactsDiffCallback(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact.id == newContact.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact == newContact
    }

}
