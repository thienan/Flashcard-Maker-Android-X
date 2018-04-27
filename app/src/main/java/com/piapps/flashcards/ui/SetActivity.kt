package com.piapps.flashcards.ui

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Set
import kotlinx.android.synthetic.main.activity_set.*
import org.jetbrains.anko.toast

class SetActivity : AppCompatActivity() {

    lateinit var set: Set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        set = Flashcards.instance.sets().get(intent.getLongExtra("id", 0L))
        supportActionBar?.title = set.title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_set, menu)
        menu.getItem(0).icon = if (set.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_star_black) else
            ContextCompat.getDrawable(this, R.drawable.ic_star_empty_black)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        if (item?.itemId == R.id.action_favorite) {
            set.isFavorite = !set.isFavorite
            Flashcards.instance.sets().put(set)
            item.icon = if (set.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_star_black) else
                ContextCompat.getDrawable(this, R.drawable.ic_star_empty_black)
            return true
        }

        if (item?.itemId == R.id.action_color) {
            toast("Set color")
            return true
        }

        if (item?.itemId == R.id.action_delete) {
            set.isTrash = true
            Flashcards.instance.sets().put(set)
            finish()
            return true
        }


        return super.onOptionsItemSelected(item)
    }

}
