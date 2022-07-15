package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject
import kotlin.reflect.full.declaredMemberProperties

object PresentationStore {
    private val _pres = arrayListOf<Presentation>()
    val pres: List<Presentation> = _pres
    private val nFields = Presentation::class.declaredMemberProperties.size

    private lateinit var queue: RequestQueue
    private const val serverUrl = "https://101.132.173.58/" /// need to be changed
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