package cn.edu.sjtu.yyqdashen.presentation

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.Toast
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

    }

    fun startPost(view: View?) = startActivity(Intent(this, PostPresentation::class.java))
    fun recentPre(view: View?) = {

        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("script", "this is a test script")
        Log.e("video_uri", PresentationStore.pre.video_uri.toString())
        PresentationStore.pre.video_uri?.toFile(context)
            ?.let { mpFD.addFormDataPart("recording","presentation", it.asRequestBody("video/mp4".toMediaType())) }
        Log.e("video_uri", PresentationStore.pre.video_uri.toString())
        val request = Request.Builder()
            //.method("POST",mpFD.build())
            .url(PresentationStore.serverUrl +"score/")
            .post(mpFD.build())
            .build()
        Log.e("video_uri", PresentationStore.pre.video_uri.toString())

        // hardcode test for UI
        PresentationStore.pre.volume_score="volume"
        PresentationStore.pre.facial_score="facial"
        PresentationStore.pre.visual_score="visual"
        PresentationStore.pre.speech_score="speech"
        PresentationStore.pre.pace_score="pace"
        PresentationStore.pre.gesture_score="gesture"
        PresentationStore.pre.suggestion="suggestion_text"

        PresentationStore.client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("getscore", "Failed request")
                Log.e("error",e.toString())
                stat = "failed"
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
                    val suggestion = try { json.getString("suggestion")} catch (e: JSONException) { "error" }

                    PresentationStore.pre.facial_score=facial_scoreReceived
                    PresentationStore.pre.visual_score=visual_scoreReceived
                    PresentationStore.pre.speech_score=speech_scoreReceived
                    PresentationStore.pre.pace_score=pace_scoreReceived
                    PresentationStore.pre.gesture_score=gesture_scoreReceived
                    PresentationStore.pre.volume_score = volume_scoreReceived
                    PresentationStore.pre.suggestion=suggestion
                    PresentationStore.pre.overall_score=overall_scoreReceived
                    Log.e("volume score is", PresentationStore.pre.volume_score!!)
                    stat = "done"
                }
            }
        startActivity(Intent(this, RecentFeedback::class.java))

    }
}