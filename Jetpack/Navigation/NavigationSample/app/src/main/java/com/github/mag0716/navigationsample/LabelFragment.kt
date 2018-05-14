package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_label.*

class LabelFragment : Fragment() {

    companion object {
        const val KEY = "label"
        fun newInstance(label: String): LabelFragment {
            return LabelFragment().apply {
                arguments = bundleOf(KEY to label)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_label, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text.text = arguments?.getString(KEY)
    }
}