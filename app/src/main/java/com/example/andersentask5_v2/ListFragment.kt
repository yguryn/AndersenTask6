package com.example.andersentask5_v2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var firstElement: LinearLayout
    private lateinit var secondElement: LinearLayout
    private lateinit var thirdElement: LinearLayout
    private lateinit var firstName: TextView
    private lateinit var firstSurName: TextView
    private lateinit var firstNumber: TextView
    private lateinit var secondName: TextView
    private lateinit var secondSurName: TextView
    private lateinit var secondNumber: TextView
    private lateinit var thirdName: TextView
    private lateinit var thirdSurName: TextView
    private lateinit var thirdNumber: TextView
    private lateinit var elementsList: ArrayList<LinearLayout>
    private lateinit var showInfo: ShowInfo

    private lateinit var currList: ArrayList<Contact>

    companion object {
        private const val CONTACT_LIST_EXTRA = "CONTACT_LIST_EXTRA"
        fun newInstance(contactList: ArrayList<Contact>) = ListFragment().also {
            it.arguments = Bundle().apply {
                putParcelableArrayList(CONTACT_LIST_EXTRA, contactList)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShowInfo) {
            showInfo = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstElement = view.findViewById(R.id.firstElement)
        secondElement = view.findViewById(R.id.secondElement)
        thirdElement = view.findViewById(R.id.thirdElement)
        firstName = view.findViewById(R.id.firstName)
        firstSurName = view.findViewById(R.id.firstSurname)
        firstNumber = view.findViewById(R.id.firstNumber)
        secondName = view.findViewById(R.id.secondName)
        secondSurName = view.findViewById(R.id.secondSurName)
        secondNumber = view.findViewById(R.id.secondNumber)
        thirdName = view.findViewById(R.id.thirdName)
        thirdSurName = view.findViewById(R.id.thirdSurName)
        thirdNumber = view.findViewById(R.id.thirdNumber)
        elementsList = arrayListOf(firstElement, secondElement, thirdElement)
        currList =
            requireArguments().getParcelableArrayList<Contact>(CONTACT_LIST_EXTRA) as ArrayList<Contact>
        elementsList.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                showInfo.onContactClicked(currList[index])
            }
        }
        setElements()
    }

    private fun setElements() {
        firstName.text = currList[0].name
        firstSurName.text = currList[0].surname
        firstNumber.text = currList[0].number
        secondName.text = currList[1].name
        secondSurName.text = currList[1].surname
        secondNumber.text = currList[1].number
        thirdName.text = currList[2].name
        thirdSurName.text = currList[2].surname
        thirdNumber.text = currList[2].number
    }
}