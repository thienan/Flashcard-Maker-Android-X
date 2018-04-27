package com.piapps.flashcards.util

import android.support.v4.content.ContextCompat
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards

/**
 * Created by abduaziz on 4/27/18.
 */

object Extensions {

    fun color(id: Long) = when (id % 18) {
        0L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c18)
        1L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c1)
        2L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c2)
        3L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c3)
        4L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c4)
        5L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c5)
        6L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c6)
        7L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c7)
        8L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c8)
        9L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c9)
        10L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c10)
        11L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c11)
        12L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c12)
        13L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c13)
        14L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c14)
        15L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c15)
        16L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c16)
        17L -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.c17)
        else -> ContextCompat.getColor(Flashcards.instance.applicationContext, R.color.colorAccent)
    }

}