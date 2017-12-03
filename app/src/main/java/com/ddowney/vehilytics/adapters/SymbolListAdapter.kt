package com.ddowney.vehilytics.adapters

import android.content.Context
import android.view.View
import com.ddowney.vehilytics.models.SymbolInformation
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.ddowney.vehilytics.R
import kotlinx.android.synthetic.main.symbol_info_row.view.*


/**
 * Created by Dan on 25/11/2017.
 * Adapter for the symbol finder activity list
 */
class SymbolListAdapter(internal val context: Context, private val symbols : List<SymbolInformation>)
    : ArrayAdapter<SymbolInformation>(context, R.layout.symbol_info_row, symbols) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val symbol = symbols[position]
        val newView : View

        newView = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.symbol_info_row, parent, false)
        } else {
            convertView
        }

        newView.symbol_name.text = symbol.symbolName
        newView.symbol_description.text = symbol.symbolDescription
        newView.symbol_image.setImageDrawable(symbol.image)

        return newView
    }
}