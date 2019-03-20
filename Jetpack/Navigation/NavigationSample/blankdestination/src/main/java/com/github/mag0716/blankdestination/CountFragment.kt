package com.github.mag0716.blankdestination


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_count.*

class CountFragment : Fragment() {

    companion object {
        const val KEY = "count"
    }

    val count: Int by lazy {
        arguments?.getInt(KEY) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_count, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = "$count"
        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_count, bundleOf(KEY to count + 1)))
    }
}
