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
    //var file: file = null.
    //script:.....
    ){

}