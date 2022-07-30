package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityLoadingBinding


class LoadActivity : AppCompatActivity() {

    private lateinit var view: ActivityLoadingBinding
    private var enableSend = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(view.root)

        val post: Thread = object : Thread() {
            override fun run() {
                try {
                    var stat:String
                    super.run()
                    PresentationStore.getAudioScore(applicationContext)

                } catch (e: Exception) {
                } finally {
                    val i = Intent(
                        this@LoadActivity,
                        Feedback::class.java
                    )
                    startActivity(i)
                    finish()
                }
            }
        }
        post.start()
    }

    // This line of code is used for skipping the waiting process of loading page,
    // should be deleted later.
    fun extractFeedback(view: View?) = startActivity(Intent(this, Feedback::class.java))

}