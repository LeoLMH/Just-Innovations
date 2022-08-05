package cn.edu.sjtu.yyqdashen.presentation

import android.graphics.Bitmap
import android.net.Uri

//TODO:: 4 add a new variable to represent script and video recording
class Presentation(
    var overall_score: String? = null,
    var speech_score: String? = null,
    var volume_score: String? = null,
    var pace_score: String? = null,
    var visual_score: String? = null,
    var gesture_score: String? = null,
    var facial_score: String? = null,
    var suggestion: String? = null,
    var video_uri: Uri? = null,
    var script_uri: Uri? = null,
    var image: Bitmap? = null,
    var gesture_suggestion: String? = null,
    var face_suggestion: String? = null,
    var volume_suggestion: String? = null,
    var pace_suggestion: String? = null,
    var flue_suggestion: String? = null,
    var memo_suggestion: String? = null,
    var memo_score: String? = null,
    var flue_score: String? = null,
    var topic: String? = null,
    var user_name: String? = null,
    var script: String? = null,
    var pre_title: String? = null,
    //var file: file = null.
    //script:.....
    ){

}