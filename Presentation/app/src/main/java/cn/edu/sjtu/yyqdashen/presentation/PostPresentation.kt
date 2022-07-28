package cn.edu.sjtu.yyqdashen.presentation

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import cn.edu.sjtu.yyqdashen.presentation.databinding.ActivityPresentationPostBinding
import cn.edu.sjtu.yyqdashen.presentation.PresentationStore.pre

class PostPresentation : AppCompatActivity() {
    private lateinit var view: ActivityPresentationPostBinding
    private var enableSend = true
    private val viewState: PostViewState by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityPresentationPostBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.videoButton.setImageResource(viewState.videoIcon)

        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    toast("${it.key} access denied")
                    finish()
                }
            }
        }.launch(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE))

        val forVideoResult =
            registerForActivityResult(ActivityResultContracts.GetContent(), fun(uri: Uri?) {
                uri?.let {
                    if (it.toString().contains("video")) {
                        viewState.videoUri = it
                        viewState.videoIcon = android.R.drawable.presence_video_busy
                        view.videoButton.setImageResource(viewState.videoIcon)
                    } else {
                        toast("Only videos are allowed")
                    }
                } ?: run { Log.d("Pick media", "failed") }
            })
        view.videoButton.setOnClickListener {
            forVideoResult.launch("*/*")
        }

        val forScriptResult =
            registerForActivityResult(ActivityResultContracts.GetContent(), fun(uri: Uri?){
                uri?.let{
                    viewState.scriptUri = it

                } ?: run {Log.d("Pick script", "failed")}
            })
        view.scriptButton.setOnClickListener{
            forScriptResult.launch("*/*")
        }



    }
    /*
    private fun submitpresentation() {
        val chatt = Chatt(username = view.usernameTextView.text.toString(),
            message = view.messageTextView.text.toString())

        postChatt(applicationContext, chatt, viewState.imageUri, viewState.videoUri) { msg ->
            runOnUiThread {
                toast(msg)
            }
            finish()
        }
    }*/
    fun fetchResult(view: View?){
        pre.volume_score="8888"
        startActivity(Intent(this, LoadActivity::class.java))
    }

    private fun mediaStoreAlloc(mediaType: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.MIME_TYPE, mediaType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        return contentResolver.insert(
            if (mediaType.contains("video"))
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values)
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        menu?.apply {
//            add(NONE, FIRST, NONE, getString(R.string.send))
//            getItem(0).setIcon(android.R.drawable.ic_menu_send).setEnabled(enableSend)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
//        }
//        return super.onPrepareOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == FIRST) {
//            enableSend = false
//            invalidateOptionsMenu()
//            submitPresentation()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    fun submitPresentation() {
//        val pre = Presentation(username = view.usernameTextView.text.toString(),
//            message = view.messageTextView.text.toString())
//
//        postPresentation(applicationContext, pre)
//        finish()
//    }

    class PostViewState: ViewModel() {
        var enableSend = true
        var videoUri: Uri? = null
        var videoIcon = android.R.drawable.presence_video_online
        var scriptUri: Uri? = null
    }

}