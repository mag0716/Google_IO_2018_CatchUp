package com.github.mag0716.navigationsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {

    companion object {
        const val KEY = "count"
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
        Log.d(getTitle(), "onCreateView")
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = getTitle()
        button.setOnClickListener {
            // parentFragment は NavHostFragment になる
            val parentFragment = parentFragment?.parentFragment
            if (parentFragment is ParentFragment) {
                parentFragment.pushFragment(count + 1)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(getTitle(), true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(getTitle(), "onDestroyView")
    }

    fun getTitle(): String = "Child$count"
}