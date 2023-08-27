package com.fininace.calculate.income.ui.result

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fininace.calculate.income.R
import com.fininace.calculate.income.data.Data
import com.fininace.calculate.income.databinding.FragmentTableBinding

class TableFragment(
    private val header: List<String>,
    private val data: List<Data>
) : Fragment(R.layout.fragment_table) {

    private lateinit var binding: FragmentTableBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTableBinding.bind(view)

        buildRow(header, true)

        data.forEach { row ->
            buildRow(row.raw())
        }
    }

    private fun buildRow(items: List<String>, isHeader: Boolean = false) {
        val row = layoutInflater.inflate(R.layout.data_row, binding.tableLayout, false) as TableRow
        for (value in items) {
            val view = layoutInflater.inflate(R.layout.grid_item, binding.tableLayout, false) as TextView
            view.text = value

            if (isHeader) {
                view.setTypeface(view.typeface, Typeface.BOLD)
            }

            row.addView(view, TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            ))
        }

        binding.tableLayout.addView(row)
    }
}