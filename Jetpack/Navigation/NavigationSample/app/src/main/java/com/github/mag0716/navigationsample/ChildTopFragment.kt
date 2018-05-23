package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_child_top.*

class ChildTopFragment : Fragment(), NavController.OnNavigatedListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_child_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            // ここで child_navigation_graph が欲しいが、sample_navigation_graph が取得されて、
            // java.lang.IllegalArgumentException: navigation destination com.github.mag0716.navigationsample:id/action_child is unknown to this NavController
            // になる
            Log.d(getTitle(), "${container.findNavController().graph.label}")
            container.findNavController().navigate(R.id.action_child)
        }
    }

    override fun onResume() {
        super.onResume()
        container.findNavController().addOnNavigatedListener(this)
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(getTitle(), true)
        }
    }

    override fun onPause() {
        super.onPause()
        container.findNavController().removeOnNavigatedListener(this)
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        Log.d(getTitle(), "onNavigated : ${destination.label}")
        button.visibility = if (destination.id == R.id.blankFragment) View.VISIBLE else View.INVISIBLE
    }

    fun getTitle(): String = "ChildTop"
}