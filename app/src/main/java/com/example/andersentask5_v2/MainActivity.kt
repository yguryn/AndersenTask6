package com.example.andersentask5_v2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), ShowInfo, SaveChanges {

    private var isLandScape: Boolean = false
    private lateinit var contactList: ArrayList<Contact>
    private lateinit var listFragment: ListFragment
    private lateinit var infoFragment: InfoFragment

    companion object {
        lateinit var showInfo: ShowInfo
        lateinit var saveChanges: SaveChanges
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showInfo = this
        saveChanges = this

        val orientation = resources.configuration.orientation
        isLandScape = orientation == Configuration.ORIENTATION_LANDSCAPE

        contactList = arrayListOf(
            Contact(0, "Vasya", "Golovach", "0993151777"),
            Contact(1, "Dima", "Silver", "0936631456"),
            Contact(2, "Grusha", "Bybna", "0315522953")
        )

        if (!isLandScape) {
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance(contactList)
                replace(R.id.frameLayout, listFragment)
                commit()
            }
        } else {
            Log.d("sss", "LandScope")
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance(contactList)
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
        contactList[contact.id].name = contact.name
        contactList[contact.id].surname = contact.surname
        contactList[contact.id].number = contact.number

        if (!isLandScape) {
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance(contactList)
                replace(R.id.frameLayout, listFragment)
                commit()
            }
        } else {
            Log.d("sss", "LandScope")
            supportFragmentManager.beginTransaction().run {
                listFragment = ListFragment.newInstance(contactList)
                infoFragment = InfoFragment.newInstance(contact)
                replace(R.id.frameLayout, listFragment)
                replace(R.id.frameLayout2, infoFragment)
                commit()
            }
        }
    }
}