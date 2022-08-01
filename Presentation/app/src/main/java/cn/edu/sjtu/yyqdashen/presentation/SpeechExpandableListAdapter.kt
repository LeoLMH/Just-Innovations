package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.HashMap

class SpeechExpandableListAdapter internal constructor(
    private val context: SpeechFragment,
    private val titleList: List<String>,
    private val dataList: Presentation
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedObject = getChild(listPosition, expandedListPosition) as Presentation
        val expandedListText = expandedObject.volume_eval
        val expandedVolumeImage = expandedObject.volume_image
        if (convertView == null) {
            val layoutInflater =
                this.context.activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.speech_list_group, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.SpeechDescription)
        expandedListTextView.text = expandedListText
        val expandedListImageView = convertView!!.findViewById<ImageView>(R.id.speech_imageView)
        // set image resource
        expandedListImageView.setImageURI(expandedObject.volume_image)
        return convertView
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return 1
    }
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.speech_list_item, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.speech_listView)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}