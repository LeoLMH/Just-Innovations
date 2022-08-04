package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject
import kotlin.reflect.full.declaredMemberProperties
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableArrayList
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import java.io.File
import java.io.IOException
import kotlin.reflect.full.declaredMemberProperties
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import cn.edu.sjtu.yyqdashen.presentation.toFile
object PresentationStore {
    private val client = OkHttpClient()
    private val _pre = Presentation()
    val pre = Presentation()
    private val nFields = Presentation::class.declaredMemberProperties.size

    private lateinit var queue: RequestQueue
    //private const val serverUrl = "https://101.132.173.58/" /// need to be changed
    //private const val serverUrl = "http://10.0.2.2:5000/"
    private const val serverUrl = "http://3.143.112.154:8000/"
    //private const val serverUrl = "http://127.0.0.1:5000/"
    //private const val serverUrl = "http://3.143.112.154:8000/"


    fun getAudioScore(context: Context):String{
        var stat:String = "not done"
        Log.e("tag","start getting audio score")
        Log.e("video_uri",pre.video_uri.toString())
        //TODO:: 4 build a request body that contains a video selected from phone
        //pre.
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("script", "this is a test script")
        Log.e("video_uri",pre.video_uri.toString())
        pre.video_uri?.toFile(context)
            ?.let { mpFD.addFormDataPart("recording","presentation", it.asRequestBody("video/mp4".toMediaType())) }
        Log.e("video_uri",pre.video_uri.toString())
        val request = Request.Builder()
            //.method("POST",mpFD.build())
            .url(serverUrl+"score/")
            .post(mpFD.build())
            .build()
        Log.e("video_uri",pre.video_uri.toString())

        // hardcode test for UI
        pre.volume_score="volume"
        pre.facial_score="facial"
        pre.visual_score="visual"
        pre.speech_score="speech"
        pre.pace_score="pace"
        pre.gesture_score="gesture"
        pre.suggestion="suggestion_text"

        client.newCall(request).enqueue(object : Callback {
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

                    pre.facial_score=facial_scoreReceived
                    pre.visual_score=visual_scoreReceived
                    pre.speech_score=speech_scoreReceived
                    pre.pace_score=pace_scoreReceived
                    pre.gesture_score=gesture_scoreReceived
                    pre.volume_score = volume_scoreReceived
                    pre.suggestion=suggestion
                    pre.overall_score=overall_scoreReceived
                    Log.e("volume score is",pre.volume_score!!)
                    stat = "done"
                    val request2 = Request.Builder()
                        //.method("POST",mpFD.build())
                        .url(serverUrl+"image/")
                        .post(mpFD.build())
                        .build()
                    client.newCall(request2).enqueue(object : Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            Log.e("getscore", "Failed second request")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.e("getscore", "Second request succeed")
                            val result = response.body?.string() ?: ""
                            var decode_result = Base64.decode(result,Base64.NO_WRAP)

                            pre.image = BitmapFactory.decodeResource(resources, R.drawable.image)
                        }
                    })
                }
            }
        })

        while(stat != "failed" && stat != "done"){
        }
        return stat
    }
}

//fun postPresentation(context: Context, pres: Presentation) {
//    val jsonObj = mapOf(
//        "username" to pres.username,
//        "message" to pres.message
//    )
//    val postRequest = JsonObjectRequest(Request.Method.POST,
//        serverUrl+"postchatt/", JSONObject(jsonObj),
//        { Log.d("postChatt", "chatt posted!") },
//        { error -> Log.e("postChatt", error.localizedMessage ?: "JsonObjectRequest error") }
//    )
//
//    if (!this::queue.isInitialized) {
//        queue = newRequestQueue(context)
//    }
//    queue.add(postRequest)
//}