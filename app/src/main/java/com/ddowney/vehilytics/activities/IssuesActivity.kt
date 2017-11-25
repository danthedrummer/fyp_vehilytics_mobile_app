package com.ddowney.vehilytics.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ddowney.vehilytics.R
import com.ddowney.vehilytics.adapters.IssuesListAdapter
import com.ddowney.vehilytics.models.IssueDetails
import kotlinx.android.synthetic.main.activity_issues.*

class IssuesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)

        val sampleIssues = listOf(
                IssueDetails("Battery Charge Alert", "Your battery voltage " +
                        "has been dropping over the last month. You should clean your battery " +
                        "contacts or check your alternator.", getDrawable(R.drawable.battery_symbol))

        )

        val issuesAdapter = IssuesListAdapter(this, sampleIssues)

        issues_list_view.adapter = issuesAdapter
        issues_list_view.divider = resources.getDrawable(R.drawable.simple_list_divider)
        issues_list_view.dividerHeight = 2
    }
}
