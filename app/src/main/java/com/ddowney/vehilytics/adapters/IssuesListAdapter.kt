package com.ddowney.vehilytics.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.models.IssueDetails
import kotlinx.android.synthetic.main.issue_detail_row.view.*

/**
 * Created by Dan on 25/11/2017.
 */
class IssuesListAdapter(internal val context: Context, private val issues : List<IssueDetails>)
    : ArrayAdapter<IssueDetails>(context, R.layout.issue_detail_row, issues) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val issue = issues[position]
        var newView : View

        newView = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            inflater.inflate(R.layout.issue_detail_row, parent, false)
        } else {
            convertView
        }

        newView.issue_header.text = issue.issueHeader
        newView.issue_description.text = issue.issueDescription
        newView.issue_image.setImageDrawable(issue.image)
        newView.issue_placeholder.text = issue.placeholder

        return newView
    }
}