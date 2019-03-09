package com.github.mag0716.navigationsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment() {

    companion object {
        val TAG = ParentFragment::class.java.simpleName
        const val KEY = "label"
    }

    val label: String by lazy {
        arguments?.getString(KEY) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate : $label")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView : $label")
        return inflater.inflate(R.layout.fragment_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            pushFragment()
        }
        text.text = label
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(label)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView : $label")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy : $label")
    }

    fun pushFragment(count: Int = 0) {
        Log.d(TAG, "pushFragment : $count")
        findNavController().navigate(R.id.actionChild, bundleOf(KEY to count))
    }
}