package com.ddowney.vehilytics.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.models.DiagnosticReading
import com.ddowney.vehilytics.models.IssueDetails
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.issue_detail_row.view.*

/**
 * Created by Dan on 25/11/2017.
 * Adapter for the issues activity list
 */
class IssuesListAdapter(internal val context: Context, private val issues : List<IssueDetails>)
    : ArrayAdapter<IssueDetails>(context, R.layout.issue_detail_row, issues) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val issue = issues[position]
        val newView : View

        newView = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.issue_detail_row, parent, false)
        } else {
            convertView
        }

        newView.issue_header.text = issue.issueHeader
        newView.issue_description.text = issue.issueDescription
        newView.issue_image.setImageDrawable(issue.image)

        newView.test_chart.clear()
        newView.test_chart.data = generateDataSets(issue.reading)
        val d = Description()
        d.text = issue.reading[0].sensorName
        newView.test_chart.description = d

        val min = issue.reading[0].rangeMin.toFloat()
        val max = issue.reading[0].rangeMax.toFloat()

        newView.test_chart.axisLeft.axisMinimum = min - ((max - min)/2)
        newView.test_chart.axisRight.setDrawLabels(false)
        newView.test_chart.xAxis.setDrawLabels(false)

        return newView
    }

    private fun generateDataSets(diagnosticReadings: List<DiagnosticReading>): LineData {

        val optimumUpperEntries = mutableListOf<Entry>()
        val optimumLowerEntries = mutableListOf<Entry>()
        val entries = mutableListOf<Entry>()
        diagnosticReadings.forEach { reading ->
            optimumUpperEntries.add(Entry(entries.size.toFloat(), reading.rangeMax.toFloat()))
            optimumLowerEntries.add(Entry(entries.size.toFloat(), reading.rangeMin.toFloat()))
            entries.add(Entry(entries.size.toFloat(), reading.value.toFloat()))
        }

        val setUpper = LineDataSet(optimumUpperEntries, "Upper Optimum")
        setUpper.axisDependency = YAxis.AxisDependency.LEFT
        setUpper.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        setUpper.circleColors = listOf(ContextCompat.getColor(context, R.color.colorIcons))
        setUpper.lineWidth = 2f
        setUpper.circleRadius = 3f
        setUpper.fillAlpha = 65
        setUpper.setDrawValues(false)

        val setLower = LineDataSet(optimumLowerEntries, "Lower Optimum")
        setLower.axisDependency = YAxis.AxisDependency.LEFT
        setLower.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        setLower.circleColors = listOf(ContextCompat.getColor(context, R.color.colorIcons))
        setLower.lineWidth = 2f
        setLower.circleRadius = 3f
        setLower.fillAlpha = 65
        setLower.setDrawValues(false)

        val setActual = LineDataSet(entries, "Actual")
        setActual.axisDependency = YAxis.AxisDependency.LEFT
        setActual.color = ContextCompat.getColor(context, R.color.colorAccent)
        setActual.circleColors = listOf(ContextCompat.getColor(context, R.color.colorDivider))
        setActual.lineWidth = 2f
        setActual.circleRadius = 3f
        setActual.fillAlpha = 65

        val data = LineData(setUpper, setLower, setActual)
        data.setValueTextColor(R.color.colorDivider)
        data.setValueTextSize(9f)

        return data
    }
}