package com.piapps.flashcards.ui.controller

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.piapps.flashcards.R
import com.piapps.flashcards.model.Set
import com.piapps.flashcards.ui.SetActivity
import com.piapps.flashcards.util.DateUtils
import com.piapps.flashcards.util.Extensions

/**
 * Created by abduaziz on 4/19/18.
 */

class SetsController(var list: ArrayList<Set>) : RecyclerView.Adapter<SetsController.SetViewHolder>() {

    enum class SORTING_ORDER{
        CREATED_TIME,LAST_EDITED
    }
    var sortingOrder: SORTING_ORDER = SORTING_ORDER.LAST_EDITED

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SetViewHolder {
        return SetViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_set, parent, false))
    }

    override fun onBindViewHolder(holder: SetViewHolder?, position: Int) {
        holder?.bind(position, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var textViewDate: TextView
        var textViewTime: TextView
        var cardView: CardView
        var textViewTitle: TextView
        var textViewCount: TextView

        init {
            itemView.setOnClickListener(this)
            textViewDate = itemView.findViewById(R.id.textViewDate)
            textViewTime = itemView.findViewById(R.id.textViewTime)
            cardView = itemView.findViewById(R.id.cardView)
            textViewTitle = itemView.findViewById(R.id.textViewSetTitle)
            textViewCount = itemView.findViewById(R.id.textViewSetCount)
        }

        fun bind(position: Int, set: Set) {

            textViewDate.visibility = VISIBLE
            if (position - 1 >= 0) {
                if (DateUtils.getDay(list[position - 1].lastEdited).equals(DateUtils.getDay(set.lastEdited))) {
                    textViewDate.visibility = GONE
                }
            }

            when (sortingOrder) {
                SORTING_ORDER.LAST_EDITED -> {
                    textViewDate.text = "${DateUtils.getDay(set.lastEdited)}"
                    textViewTime.text = "${DateUtils.getHourMinute(set.lastEdited)}"
                }
                SORTING_ORDER.CREATED_TIME -> {
                    textViewDate.text = "${DateUtils.getDay(set.id)}"
                    textViewTime.text = "${DateUtils.getHourMinute(set.id)}"
                }
            }
            textViewTitle.text = set.title
            textViewCount.text = "${set.count} cards"

            //todo: set the set color if it exists
            cardView.setCardBackgroundColor(Extensions.color(set.id))
        }

        override fun onClick(p0: View?) {
            val intent = Intent(itemView.context, SetActivity::class.java)
            intent.putExtra("id", list[adapterPosition].id)
            itemView.context.startActivity(intent)
        }
    }
}