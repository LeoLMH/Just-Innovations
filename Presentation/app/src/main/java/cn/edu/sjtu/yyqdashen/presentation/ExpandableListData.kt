package cn.edu.sjtu.yyqdashen.presentation

import android.net.Uri
import android.os.Environment
import java.io.File
import java.util.*
internal object ExpandableListData {
    val data: Presentation
        get() {
            val expandableListDetail = Presentation()

            expandableListDetail.facial_score = 100.toString()
            expandableListDetail.gesture_score = 100.toString()
            expandableListDetail.pace_score = 100.toString()
            expandableListDetail.script_uri = null
            expandableListDetail.speech_score = 100.toString()
            expandableListDetail.suggestion = "no suggestion"
            expandableListDetail.video_uri = null
            expandableListDetail.volume_eval = "sample eval"
            expandableListDetail.visual_score = 100.toString()
            expandableListDetail.overall_score = 100.toString()
            expandableListDetail.volume_score = 100.toString()
            // set the image to display in the expandable listS
            expandableListDetail.volume_image = Uri.parse("http://stackoverflow.com")


            return expandableListDetail
        }

}