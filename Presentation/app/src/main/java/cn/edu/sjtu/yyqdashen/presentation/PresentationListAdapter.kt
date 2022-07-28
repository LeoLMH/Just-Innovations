package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import cn.edu.sjtu.yyqdashen.presentation.databinding.ListItemPresentationBinding
/*
class PresentationListAdapter(context: Context, pres: List<Presentation>) :
    ArrayAdapter<Presentation>(context, 0, pres) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = (convertView?.tag /* reuse binding */ ?: run {
            val rowView = LayoutInflater.from(context).inflate(R.layout.list_item_presentation, parent, false)
            rowView.tag = ListItemPresentationBinding.bind(rowView) // cache binding
            rowView.tag
        }) as ListItemPresentationBinding

        getItem(position)?.run {
            val score = "50"
            listItemView.scoreTextView.text = score
            var grade_color = "#000000"
            if(score?.toInt()!! < 60){
                grade_color = "#ff0000"
            }
            else if(score?.toInt()!! < 80){
                grade_color = "#FFA500"
            }
            else{
                grade_color = "#00FF00"
            }

            listItemView.scoreTextView.setBackgroundColor(Color.parseColor(grade_color))
            listItemView.titleTextView.text = title
            listItemView.timestampTextView.text = timestamp
            listItemView.root.setBackgroundColor(Color.parseColor(grade_color))

        }

        return listItemView.root
    }
}*/

