package cn.edu.sjtu.yyqdashen.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityRecentFeedbackBinding


class RecentFeedback : AppCompatActivity() {
    private lateinit var view: ActivityRecentFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityRecentFeedbackBinding.inflate(layoutInflater)
        setContentView(view.root)
    }

//    private fun refreshTimeline() {
//        getChatts(applicationContext) {
//            runOnUiThread {
//                // inform the list adapter that data set has changed
//                // so that it can redraw the screen.
//                chattListAdapter.notifyDataSetChanged()
//            }
//            // stop the refreshing animation upon completion:
//            view.refreshContainer.isRefreshing = false
//        }
//    }
}