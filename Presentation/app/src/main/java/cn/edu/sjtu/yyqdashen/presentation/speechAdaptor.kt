package cn.edu.sjtu.yyqdashen.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentSpeechBinding
import cn.edu.sjtu.yyqdashen.presentation.databinding.ListItemPresentationBinding
/*
class speechAdapter(context: Context, pres: Presentation) :
    lateinit var binding: FragmentSpeechBinding

    ArrayAdapter<Presentation>(context, 0, pres) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = (convertView?.tag /* reuse binding */ ?: run {
            val rowView = LayoutInflater.from(context).inflate(R.layout.list_item_presentation, parent, false)
            rowView.tag = ListItemPresentationBinding.bind(rowView) // cache binding
            rowView.tag
        }) as ListItemPresentationBinding

        getItem(position)?.run {
            bindings
        }

        return listItemView.root
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpeechBinding.inflate(layoutInflater)
        return binding.root
    }
}
*/
