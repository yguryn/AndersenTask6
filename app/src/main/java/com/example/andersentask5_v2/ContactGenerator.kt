package com.example.andersentask5_v2

class ContactGenerator {
    fun generateContacts(): ArrayList<Contact> {
        var contactList = ArrayList<Contact>()
        (0..101).forEach {
            contactList.add(
                Contact(
                    it,
                    "name $it",
                    "surname $it",
                    (0..999999999).random().toString()
                )
            )
        }
        return contactList
    }
}