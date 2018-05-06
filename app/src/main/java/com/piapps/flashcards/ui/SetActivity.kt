package com.piapps.flashcards.ui

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.piapps.flashcards.R
import com.piapps.flashcards.application.Flashcards
import com.piapps.flashcards.model.Card
import com.piapps.flashcards.model.Card_
import com.piapps.flashcards.model.Set
import com.piapps.flashcards.ui.controller.SetController
import com.piapps.flashcards.ui.fragment.CardFragment
import com.piapps.flashcards.util.Extensions
import com.piapps.flashcards.util.toColor
import com.piapps.flashcards.util.toHexColor
import eltos.simpledialogfragment.SimpleDialog
import eltos.simpledialogfragment.color.SimpleColorDialog
import eltos.simpledialogfragment.input.SimpleInputDialog
import kotlinx.android.synthetic.main.activity_set.*

class SetActivity : AppCompatActivity(), SimpleDialog.OnDialogResultListener {

    lateinit var set: Set

    // dialogs
    val DIALOG_SET_COLOR = "DIALOG_SET_COLOR"
    val DIALOG_SET_NAME = "DIALOG_SET_NAME"

    lateinit var setController: SetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        set = Flashcards.instance.sets().get(intent.getLongExtra("id", 0L))
        supportActionBar?.title = set.title

        val isNewlyCreatedSet = intent.getBooleanExtra("isNew", false)
        if (isNewlyCreatedSet) {
            SimpleInputDialog()
                    .text(set.title)
                    .title(R.string.set_name)
                    .show(this, DIALOG_SET_NAME)
        }

        if (!set.color.isBlank()) {
            toolbar.setBackgroundColor(set.color.toColor())
            changeStatusBarColor(set.color.toColor())
        } else {
            toolbar.setBackgroundColor(Extensions.color(set.id))
            changeStatusBarColor(Extensions.color(set.id))
        }

        toolbar.setOnClickListener {
            SimpleInputDialog()
                    .text(set.title)
                    .title(R.string.set_name)
                    .show(this, DIALOG_SET_NAME)
        }

        setController = SetController(set.id, supportFragmentManager)
        viewPager.adapter = setController

        val query = Flashcards.instance.cards()
                .query().equal(Card_.setId, set.id)
                .build()
        query.find().forEach {
            setController.addFragment(CardFragment.newInstance(it.id))
        }

        fab.setOnClickListener {
            addNewCard()
        }
    }

    fun addNewCard() {
        val card = Card(System.currentTimeMillis(), set.id)
        Flashcards.instance.cards().put(card)
        setController.addFragment(CardFragment.newInstance(card.id))
        viewPager.currentItem = setController.list.size - 1
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
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            item.icon = if (set.isFavorite) ContextCompat.getDrawable(this, R.drawable.ic_star_black) else
                ContextCompat.getDrawable(this, R.drawable.ic_star_empty_black)
            return true
        }

        if (item?.itemId == R.id.action_color) {
            val preset = if (!set.color.isBlank()) set.color.toColor() else ContextCompat.getColor(this, R.color.c1)
            SimpleColorDialog()
                    .title(R.string.set_color)
                    .colors(Extensions.setColors())
                    .colorPreset(preset)
                    .cancelable(false)
                    .show(this, DIALOG_SET_COLOR)
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

    override fun onResult(dialogTag: String, which: Int, extras: Bundle): Boolean {
        if (dialogTag == DIALOG_SET_COLOR) {
            val color = extras.getInt(SimpleColorDialog.COLOR)
            val hexColor = color.toHexColor()
            set.color = hexColor
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            // update ui
            toolbar.setBackgroundColor(color)
            changeStatusBarColor(color)
        }

        if (dialogTag == DIALOG_SET_NAME) {
            val title = extras.getString(SimpleInputDialog.TEXT)
            set.title = title
            set.lastEdited = System.currentTimeMillis()
            Flashcards.instance.sets().put(set)
            // update ui
            toolbar.title = title
        }

        return true
    }

    fun changeStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            Extensions.colorDarker(color)?.let {
                window.statusBarColor = it
            }
        }
    }

    override fun onPause() {
        set.count = setController.list.size
        Flashcards.instance.sets().put(set)
        super.onPause()
    }

    override fun onDestroy() {
        set.count = setController.list.size
        Flashcards.instance.sets().put(set)
        super.onDestroy()
    }

}
