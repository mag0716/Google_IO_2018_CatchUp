package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {

    companion object {
        const val KEY = "count"
        fun newInstance(value: Int = 0): ChildFragment {
            return ChildFragment().apply {
                arguments = bundleOf(KEY to value)
            }
        }
    }

    val count: Int by lazy {
        val bundle = arguments
        if (bundle != null) {
            bundle.getInt(KEY)
        } else {
            0
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            Log.d(getTitle(), "${view.findNavController().graph.label}, ${Navigation.findNavController(view).graph.label}")
            Navigation.findNavController(view).navigate(R.id.action_child)
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(getTitle(), true)
        }
    }

    fun getTitle(): String = "Child$count"
}