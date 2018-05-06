package com.piapps.flashcards.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.piapps.flashcards.R
import kotlinx.android.synthetic.main.fragment_card.*

/**
 * Created by abduaziz on 5/6/18.
 */
class CardFragment : Fragment() {

    companion object {
        fun newInstance(id: Long): CardFragment {
            val fragment = CardFragment()
            val bundle = Bundle()
            bundle.putLong("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }


    var id: Long = 1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments.getLong("id", 1L)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "Card ID: $id"
    }

}