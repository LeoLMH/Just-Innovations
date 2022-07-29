package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject
import kotlin.reflect.full.declaredMemberProperties
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toFile
import androidx.databinding.ObservableArrayList
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import kotlin.reflect.full.declaredMemberProperties

object PresentationStore {
    private val client = OkHttpClient()
    private val _pre = Presentation()
    val pre = Presentation()
    private val nFields = Presentation::class.declaredMemberProperties.size

    private lateinit var queue: RequestQueue
    //private const val serverUrl = "https://101.132.173.58/" /// need to be changed
    private const val serverUrl = "http://10.0.2.2:5000/"
//    private const val serverUrl = "http://3.143.112.154:8000/"
    //private const val serverUrl = "http://127.0.0.1:5000/"
    //private const val serverUrl = "http://3.143.112.154:8000/"
    fun postPresentation(){
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("script", "this is a test script")
        pre.video_uri?.run {
            toFile()?.let{
                mpFD.addFormDataPart("recording","presentation",it.asRequestBody("mp4".toMediaType()))
            }
        }
        val request = Request.Builder()
            .url(serverUrl+"postpresentation/").post(mpFD.build()).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    //getScore()
                }
            }
        })
    }

    fun getAudioScore():String{
        var stat:String = "not done"
        Log.e("tag","start getting audio score")
        Log.e("video_uri",pre.video_uri.toString())
        //TODO:: 4 build a request body that contains a video selected from phone
        //pre.
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("script", "this is a test script")
        pre.video_uri?.run {
            toFile()?.let{
                mpFD.addFormDataPart("recording","presentation",it.asRequestBody("mp4".toMediaType()))
            }
        }
        Log.e("video_uri",pre.video_uri.toString())
        val request = Request.Builder()
            //.method("POST",mpFD.build())
            .url(serverUrl+"score/")
            .post(mpFD.build())
            .build()
        pre.volume_score="volume"
        pre.facial_score="facial"
        pre.visual_score="visual"
        pre.speech_score="speech"
        pre.pace_score="pace"
        pre.gesture_score="gesutre"
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("getscore", "Failed request")
                Log.e("error",e.toString())
                stat = "failed"
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val volume_scoreReceived = try { JSONObject(response.body?.string() ?: "").getString("volume_score")} catch (e: JSONException) { "error" }

                    pre.volume_score = volume_scoreReceived
                    Log.e("volume score is",pre.volume_score!!)
                    stat = "done"
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