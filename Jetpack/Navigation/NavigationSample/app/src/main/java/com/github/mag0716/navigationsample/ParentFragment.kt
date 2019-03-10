package com.github.mag0716.navigationsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment() {

    companion object {
        val TAG = ParentFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            pushFragment()
        }
    }

    private fun pushFragment(count: Int = 0) {
        Log.d(TAG, "pushFragment : $count")
        val action = ChildFragmentDirections.actionChild()
        action.count = count
        findNavController().navigate(action)
    }
}