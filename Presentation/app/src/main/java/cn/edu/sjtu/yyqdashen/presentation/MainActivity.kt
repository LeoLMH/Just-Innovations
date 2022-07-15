package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)
    }

    fun startPost(view: View?) = startActivity(Intent(this, PostPresentation::class.java))
    fun recentPre(view: View?) = startActivity(Intent(this, RecentFeedback::class.java))
}