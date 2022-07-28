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
    lateinit var binding: FragmentVisualBinding
    private var expandableListView: ExpandableListView? = null

    private var overallVisualView: TextView? = null
    private var gestureView: TextView? = null
    private var facialView: TextView? = null

    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandableListView = binding.visualExpendableList
        //Log.e("visual_score", pre.visual_score!!)
        //Log.e("gesture_score", pre.gesture_score!!)
        //Log.e("facial_score", pre.facial_score!!)
        overallVisualView=binding.visualScore
        gestureView=binding.gestureScore
        facialView=binding.facialScore

        overallVisualView!!.text=pre.visual_score
        gestureView!!.text=pre.gesture_score
        facialView!!.text=pre.facial_score

        if (expandableListView != null) {
            val listData = ExpandableListData.data
            titleList = ArrayList(listData.keys)
            adapter = VisualExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    context,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }

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