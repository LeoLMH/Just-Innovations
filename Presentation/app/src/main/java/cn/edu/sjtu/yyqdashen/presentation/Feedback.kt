package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityFeedbackBinding

class Feedback : AppCompatActivity() {

    lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        //set actionbar title
//        actionbar!!.title = "Feedback"
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.overallReturnButton.setOnClickListener {
            replaceFragment(OverallFragment())
        }

        binding.speechButton.setOnClickListener{
            replaceFragment(SpeechFragment())
        }

        binding.visualButton.setOnClickListener {
            replaceFragment(VisualFragment())
        }

        binding.suggestionButton.setOnClickListener {
            replaceFragment(SuggestionFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}