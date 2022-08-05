package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.Toast
import cn.edu.sjtu.yyqdashen.presentation.PresentationStore.pre
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private var username_text : String? = null

    private val client = OkHttpClient()
    private val _pre = Presentation()
    //private val serverUrl = "http://3.143.112.154:8000/"
    private val serverUrl = "http://10.0.2.2:5000/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

    }

    fun startPost(view: View?)  {
        username_text = findViewById<EditText>(R.id.usernameTextView).text.toString()
        pre.user_name = username_text
        startActivity(Intent(this, PostPresentation::class.java))
    }
    fun recentPre(view: View?)  {
        username_text = findViewById<EditText>(R.id.usernameTextView).text.toString()
        pre.user_name = username_text
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
        pre.user_name?.let { mpFD.addFormDataPart("username", it) }
        val request = Request.Builder()
            //.method("POST",mpFD.build())
            .url(serverUrl +"recent/")
            .post(mpFD.build())
            .build()
        var stat = "empty"
        Log.e("error","start sending request")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("recent", "Failed request")
                stat="fail"
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = JSONObject(response.body?.string() ?: "")
                    val volume_scoreReceived = try { json.getString("volume_score")} catch (e: JSONException) { "error" }

                    val facial_scoreReceived = try { json.getString("facial_score")} catch (e: JSONException) { "error" }

                    val visual_scoreReceived = try { json.getString("visual_score")} catch (e: JSONException) { "error" }

                    val speech_scoreReceived = try { json.getString("speech_score")} catch (e: JSONException) { "error" }
                    val pace_scoreReceived = try { json.getString("pace_score")} catch (e: JSONException) { "error" }
                    val gesture_scoreReceived = try { json.getString("gesture_score")} catch (e: JSONException) { "error" }
                    val overall_scoreReceived = try { json.getString("overall_score")} catch (e: JSONException) { "error" }
                    val gesture_sug = try { json.getString("gesture_sug")} catch (e: JSONException) { "error" }
                    val face_sug = try { json.getString("face_sug")} catch (e: JSONException) { "error" }
                    val vol_sug = try { json.getString("vol_sug")} catch (e: JSONException) { "error" }
                    val pace_sug = try { json.getString("pace_sug")} catch (e: JSONException) { "error" }
                    val flue_sug = try { json.getString("flue_sug")} catch (e: JSONException) { "error" }
                    val memo_sug = try { json.getString("memo_sug")} catch (e: JSONException) { "error" }
                    val overall_suggestion = try { json.getString("suggestion")} catch (e: JSONException) { "error" }
                    val flue_scoreReceived = try { json.getString("flue_score")} catch (e: JSONException) { "error" }
                    val memo_scoreReceived = try { json.getString("memo_score")} catch (e: JSONException) { "error" }

                    pre.facial_score=facial_scoreReceived
                    pre.visual_score=visual_scoreReceived
                    pre.speech_score=speech_scoreReceived
                    pre.pace_score=pace_scoreReceived
                    pre.gesture_score=gesture_scoreReceived
                    pre.volume_score = volume_scoreReceived
                    pre.suggestion=overall_suggestion
                    pre.overall_score=overall_scoreReceived
                    pre.gesture_suggestion = gesture_sug
                    pre.face_suggestion = face_sug
                    pre.volume_suggestion = vol_sug
                    pre.pace_suggestion = pace_sug
                    pre.flue_suggestion = flue_sug
                    pre.memo_suggestion = memo_sug
                    pre.flue_score = flue_scoreReceived
                    pre.memo_score = memo_scoreReceived
                    stat="success"
                }
            }
        })

        while(stat=="empty"){

        }
        startActivity(Intent(this, Feedback::class.java))

    }
}

