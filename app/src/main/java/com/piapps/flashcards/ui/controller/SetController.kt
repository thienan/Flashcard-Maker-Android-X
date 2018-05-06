package com.piapps.flashcards.ui.controller

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Card_
import com.piapps.flashcards.ui.fragment.CardFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by abduaziz on 5/6/18.
 */

class SetController(val id: Long, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val list = arrayListOf<Fragment>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    fun addFragment(fragment: Fragment) {
        list.add(fragment)
        notifyDataSetChanged()
    }

}