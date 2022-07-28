package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject
import kotlin.reflect.full.declaredMemberProperties
import android.net.Uri
import androidx.databinding.ObservableArrayList
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
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
    private const val serverUrl = "http://127.0.0.1:5000/"
    fun postPresentation(){
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("script", "this is a test script")
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

    fun getAudioScore() {
        Log.e("tag","start getting audio score")
        val mpFD = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("username", "")
        val request = Request.Builder()
            //.method("POST",mpFD.build())
            .url(serverUrl+"audio/")
            .post(mpFD.build())
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("getscore", "Failed GET request")
                getAudioScore()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val volume_scoreReceived = try { JSONObject(response.body?.string() ?: "").getString("volume_score")} catch (e: JSONException) { "error" }

                    pre.volume_score = volume_scoreReceived
                    Log.e("volume score is",pre.volume_score!!)
                }
            }
        })
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