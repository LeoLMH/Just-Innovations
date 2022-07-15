package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityLoadingBinding

class LoadActivity : AppCompatActivity() {
    private lateinit var view: ActivityLoadingBinding
    private var enableSend = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(view.root)
    }

    fun extractFeedback(view: View?) = startActivity(Intent(this, Feedback::class.java))

}