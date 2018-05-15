package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment() {

    companion object {
        const val KEY = "label"
        fun newInstance(label: String): ParentFragment {
            return ParentFragment().apply {
                arguments = bundleOf(KEY to label)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            pushFragment(0)
        }
    }

    fun pushFragment(count: Int) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ChildFragment.newInstance(count))
        transaction.addToBackStack("$count")
        transaction.commit()
    }
}