package com.github.mag0716.navigationsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment() {

    companion object {
        val TAG = ChildFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val count = arguments?.let {
            ChildFragmentArgs.fromBundle(it).count
        } ?: 0

        button.text = "Child$count"
        button.setOnClickListener {
            pushFragment(count + 1)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    private fun pushFragment(count: Int) {
        Log.d(TAG, "pushFragment : $count")
        val action = ChildFragmentDirections.actionChild()
        action.count = count
        findNavController().navigate(action)
    }
}