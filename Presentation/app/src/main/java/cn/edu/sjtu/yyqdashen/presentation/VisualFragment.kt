package cn.edu.sjtu.yyqdashen.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentVisualBinding
import cn.edu.sjtu.yyqdashen.presentation.PresentationStore.pre

//TODO:: 2 add expandable like speech fragment
class VisualFragment : Fragment(R.layout.fragment_visual) {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentVisualBinding
    private var overall_visualView: TextView? = null
    private var gestureView: TextView? = null
    private var facialView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.e("visual_score", pre.visual_score!!)
        //Log.e("gesture_score", pre.gesture_score!!)
        //Log.e("facial_score", pre.facial_score!!)
        overall_visualView=binding.visualScore
        gestureView=binding.gestureScore
        facialView=binding.facialScore

        overall_visualView!!.text=pre.visual_score
        gestureView!!.text=pre.gesture_score
        facialView!!.text=pre.facial_score


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVisualBinding.inflate(layoutInflater)
        return binding.root
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpeechFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SpeechFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}