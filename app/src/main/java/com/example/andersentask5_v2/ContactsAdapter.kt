package com.example.andersentask5_v2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.andersentask5_v2.MainActivity.Companion.deleteContact
import com.example.andersentask5_v2.MainActivity.Companion.showInfo
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class ContactsAdapter(private val contactList: List<Contact>, private var cont: Context) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>(), Filterable {

    var contactListFiltered: ArrayList<Contact> = ArrayList()

    init {
        contactListFiltered.addAll(contactList)
    }

    class ViewHolder(itemView: View, private var context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.tvName)
        private val surName = itemView.findViewById<TextView>(R.id.tvSurName)
        private val number = itemView.findViewById<TextView>(R.id.tvNumber)
        private val image = itemView.findViewById<ImageView>(R.id.ivContact)

        fun bind(contact: Contact) {
            name.text = contact.name
            surName.text = contact.surname
            number.text = contact.number
            Picasso.with(context)
                .load("https://picsum.photos/id/${contact.id + 1}/150/150")
                .into(image)
            itemView.setOnClickListener {
                showInfo.onContactClicked(contact)
            }
            itemView.setOnLongClickListener {
                deleteContact.onLongClicked(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.contact_item, parent, false)
        return ViewHolder(contactView, cont)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact: Contact = contactListFiltered[position]
        holder.bind(contact)

    }

    override fun getItemCount() = contactListFiltered.size

    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): Filter.FilterResults {
                var listFilter = ArrayList<Contact>();
                if (p0 == null || p0.isEmpty()) {
                    listFilter.addAll(contactList)
                } else {
                    val filterPattern: String = p0.toString().toLowerCase().trim()
                    for (item in contactList) {
                        if (item.name.toLowerCase()
                                .contains(filterPattern) or item.surname.toLowerCase()
                                .contains(filterPattern) or item.number.contains(filterPattern)
                        ) {
                            listFilter.add(item)
                        }
                    }
                }
                val results = Filter.FilterResults()
                results.values = listFilter
                return results;
            }

            override fun publishResults(p0: CharSequence?, p1: Filter.FilterResults?) {

                contactListFiltered.clear()
                contactListFiltered.addAll(p1?.values as Collection<Contact>)

                notifyDataSetChanged()
            }
        }
    }

    fun updateContacts(newContactList: ArrayList<Contact>) {
        val diffUtilCallback = ContactDiffUtilCallback(MainActivity.contactList, newContactList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}