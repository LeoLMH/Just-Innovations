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
import cn.edu.sjtu.yyqdashen.presentation.ExpandableListData.data
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentSpeechBinding

import cn.edu.sjtu.yyqdashen.presentation.PresentationStore.pre
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpeechFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeechFragment() : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSpeechBinding
    private var expandableListView: ExpandableListView? = null

    private var volumeScoreView: TextView? = null
    private var paceScoreView: TextView? = null
    private var speechScoreView: TextView? = null

    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expandableListView = binding.speechExpendableList
//        binding.volumeScore.text= pre.volume_score
//        binding.speechScore.text = pre.speech_score
//        binding.paceScore.text = pre.pace_score
//        Log.e("volume_score", pre.volume_score!!)
        volumeScoreView= binding.volumeScore
        speechScoreView=binding.speechScore
        paceScoreView=binding.paceScore
        volumeScoreView!!.text = pre.volume_score
        speechScoreView!!.text=pre.speech_score
        paceScoreView!!.text=pre.pace_score
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList()
            (titleList as ArrayList<String>).add("Volume")
            adapter = SpeechExpandableListAdapter(this, titleList as ArrayList<String>, listData)
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
//            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
//                Toast.makeText(
//                    context,
//                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
//                            titleList as
//                                    ArrayList<String>
//                            )
//                            [groupPosition]]!!.get(
//                        childPosition
//                    ),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpeechBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpeechFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}