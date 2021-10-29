package com.example.andersentask5_v2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var contactList: ArrayList<Contact>
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var adapter: ContactsAdapter
        private const val CONTACT_LIST_EXTRA = "CONTACT_LIST_EXTRA"
        fun newInstance() = ListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        contactList = MainActivity.contactList
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.addItemDecoration(itemDecoration)
        adapter = ContactsAdapter(contactList, requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })
    }

    fun update(newContactList: ArrayList<Contact>) {
        adapter.updateContacts(newContactList)
    }
}