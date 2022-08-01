package cn.edu.sjtu.yyqdashen.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.edu.sjtu.yyqdashen.presentation.PresentationStore.pre
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentSpeechBinding
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentSuggestionBinding
import cn.edu.sjtu.yyqdashen.presentation.databinding.FragmentVisualBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuggestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuggestionFragment : Fragment() {
    lateinit var binding: FragmentSuggestionBinding
    private var suggestionView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuggestionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suggestionView = binding.suggestionTextView

        suggestionView!!.text = pre.suggestion
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                SuggestionFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}