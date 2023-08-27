package com.owe.track.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.owe.track.R
import com.owe.track.data.IntervalUnit


class UnitAdapter(
    context: Context,
    items: List<IntervalUnit>
) : ArrayAdapter<IntervalUnit>(context, R.layout.item_unit, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem: IntervalUnit = getItem(position) as IntervalUnit

        val view = convertView ?:
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_unit, parent, false)

        (view as TextView).text = view.context.getString(currentItem.textRes)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem: IntervalUnit = getItem(position) as IntervalUnit

        val view = convertView ?:
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_unit_dropdown, parent, false)

        (view as TextView).text = view.context.getString(currentItem.textRes)
        return view
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem: IntervalUnit = getItem(position) as IntervalUnit

        val view = convertView ?:
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_unit, parent, false)

        (view as TextView).text = view.context.getString(currentItem.textRes)
        return view
    }
}
