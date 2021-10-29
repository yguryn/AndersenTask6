package com.example.andersentask5_v2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var etName: EditText
    private lateinit var etSurName: EditText
    private lateinit var etNumber: EditText
    private lateinit var saveButton: Button

    private lateinit var curContact: Contact

    companion object {
        private const val CONTACT_LIST_EXTRA = "CONTACT_LIST_EXTRA"
        fun newInstance(contact: Contact) = InfoFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(CONTACT_LIST_EXTRA, contact)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName = view.findViewById(R.id.etName)
        etSurName = view.findViewById(R.id.etSurName)
        etNumber = view.findViewById(R.id.etNumber)
        saveButton = view.findViewById(R.id.saveChanges)

        curContact = requireArguments().getParcelable(CONTACT_LIST_EXTRA)!!

        etName.setText(curContact.name)
        etSurName.setText(curContact.surname)
        etNumber.setText(curContact.number)

        saveButton.setOnClickListener {
            MainActivity.saveChanges.onButtonClicked(
                Contact(
                    curContact.id,
                    etName.text.toString(),
                    etSurName.text.toString(),
                    etNumber.text.toString()
                )
            )
        }
    }
}