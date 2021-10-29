package com.example.andersentask5_v2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import java.util.Collections.copy

class MainActivity : AppCompatActivity(R.layout.activity_main), SaveChanges, ShowInfo,
    DeleteContact {

    private var isLandScape: Boolean = false

    private lateinit var listFragment: ListFragment
    private lateinit var infoFragment: InfoFragment

    companion object {
        var contactList = ContactGenerator().generateContacts()
        var newContactList: ArrayList<Contact> = contactList.clone() as ArrayList<Contact>
        lateinit var showInfo: ShowInfo
        lateinit var saveChanges: SaveChanges
        lateinit var deleteContact: DeleteContact
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showInfo = this
        saveChanges = this
        deleteContact = this

        val orientation = resources.configuration.orientation
        isLandScape = orientation == Configuration.ORIENTATION_LANDSCAPE

        if (!isLandScape) {
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance()
                Log.d("AAA", "ASfg")
                replace(R.id.frameLayout, listFragment)
                commit()
            }
        } else {
            Log.d("sss", "LandScope")
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance()
                infoFragment = InfoFragment.newInstance(Contact(-1, "", "", ""))
                replace(R.id.frameLayout, listFragment)
                replace(R.id.frameLayout2, infoFragment)
                commit()
            }
        }
    }

    override fun onContactClicked(contact: Contact) {
        supportFragmentManager.beginTransaction().run {
            val infoFragment = InfoFragment.newInstance(contact)
            if (!isLandScape) {
                addToBackStack("infoElement")
                replace(R.id.frameLayout, infoFragment)
                commit()
            } else {
                replace(R.id.frameLayout2, infoFragment)
                commit()
            }
        }
    }

    override fun onButtonClicked(contact: Contact) {
        var index = 0
        for (i in contactList) {
            if (i.id == contact.id) break
            else index++
        }
        contactList[index] = contact
        newContactList[index] = contact
        if (!isLandScape) {
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance()
                replace(R.id.frameLayout, listFragment)
                commit()
            }
        } else {
            Log.d("sss", "LandScope")
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance()
                infoFragment = InfoFragment.newInstance(contact)
                replace(R.id.frameLayout, listFragment)
                replace(R.id.frameLayout2, infoFragment)
                commit()
            }
        }
    }

    override fun onLongClicked(contact: Contact): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete item")
        builder.setMessage("You wanna delete ${contact.name}?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            newContactList.remove(contact)
            ListFragment().update(newContactList)
            contactList.remove(contact)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }

        builder.show()
        return true
    }
}
