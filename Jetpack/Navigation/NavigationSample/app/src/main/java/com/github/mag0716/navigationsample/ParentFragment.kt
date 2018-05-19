package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    companion object {
        val TAG = ParentFragment::class.java.simpleName
        const val KEY = "label"
        fun newInstance(label: String): ParentFragment {
            return ParentFragment().apply {
                arguments = bundleOf(KEY to label)
            }
        }
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
            pushFragment(0)
        }
        text.text = label
    }

    override fun onResume() {
        super.onResume()
//        childFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
//        childFragmentManager.removeOnBackStackChangedListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView : $label")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy : $label")
    }

    override fun onBackStackChanged() {
        val activity = activity
        if (activity is MainActivity) {
            activity.updateToolbar(getCurrentFragmentTitle(), needsUpKey())
        }
    }

    fun pushFragment(count: Int) {
//        val transaction = childFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, ChildFragment.newInstance(count))
//        transaction.addToBackStack("$count")
//        transaction.commit()
    }

    fun popAllFragment() {
//        childFragmentManager.popBackStack("0", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun getCurrentFragmentTitle(): String {
//        if (childFragmentManager.backStackEntryCount > 0) {
//            val fragment = childFragmentManager.findFragmentById(R.id.container)
//            if (fragment is ChildFragment) {
//                return fragment.getTitle()
//            }
//        } else {
//            return label
//        }
        return ""
    }

    private fun needsUpKey(): Boolean {
//        return childFragmentManager.backStackEntryCount > 0
        return false
    }
}