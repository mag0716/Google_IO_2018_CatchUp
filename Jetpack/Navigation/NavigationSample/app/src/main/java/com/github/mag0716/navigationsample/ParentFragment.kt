package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment(), NavController.OnNavigatedListener {

    companion object {
        val TAG = ParentFragment::class.java.simpleName
        const val KEY = "label"
    }

    private lateinit var container: NavHostFragment

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
        container = childFragmentManager.findFragmentById(R.id.containerParent) as NavHostFragment
        button.setOnClickListener {
            pushFragment()
        }
        text.text = label
    }

    override fun onResume() {
        super.onResume()
        container.findNavController().addOnNavigatedListener(this)
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(label)
        }
    }

    override fun onPause() {
        super.onPause()
        container.findNavController().removeOnNavigatedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView : $label")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy : $label")
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        parentViews.visibility = if (destination.id == R.id.blankFragment) View.VISIBLE else View.GONE
    }

    fun pushFragment(count: Int = 0) {
        Log.d(TAG, "pushFragment : $count")
        container.findNavController().navigate(R.id.action_child, bundleOf(KEY to count))
    }
}