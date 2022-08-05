package cn.edu.sjtu.yyqdashen.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private var volumeImageView: ImageView? = null
    private var paceScoreView: TextView? = null
    private var paceImageView: ImageView? = null
    private var speechScoreView: TextView? = null
    private  var memoScoreView: TextView? = null
    private  var flueScoreView: TextView? = null
    private  var memoDesView: TextView? = null
    private  var flueDesView: TextView? = null
    private  var paceDesView: TextView? = null
    private  var volumeDesView: TextView? = null
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
//        expandableListView = binding.
//        binding.volumeScore.text= pre.volume_score
//        binding.speechScore.text = pre.speech_score
//        binding.paceScore.text = pre.pace_score
//        Log.e("volume_score", pre.volume_score!!)
        volumeScoreView=binding.volumeScore
        volumeImageView=binding.volumeImage
        speechScoreView=binding.speechScore
        memoScoreView=binding.contentScore
        memoDesView=binding.contentDesp
        flueScoreView=binding.flueScore
        flueDesView=binding.flueDesp
        paceDesView=binding.paceDesp
        volumeDesView=binding.volumnDesp
        paceScoreView=binding.paceScore
        paceImageView=binding.paceImage

        volumeScoreView!!.text = pre.volume_score
        speechScoreView!!.text=pre.speech_score
        paceScoreView!!.text=pre.pace_score
        memoScoreView!!.text=pre.memo_score
        memoDesView!!.text=pre.memo_suggestion
        paceDesView!!.text=pre.pace_suggestion
        flueScoreView!!.text=pre.flue_score
        flueDesView!!.text=pre.flue_suggestion
        volumeDesView!!.text=pre.volume_suggestion

        // Change the image from backend here!!!
        volumeImageView!!.setImageResource(R.drawable.volume_figure)
        paceImageView!!.setImageResource(R.drawable.pace_figure)



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