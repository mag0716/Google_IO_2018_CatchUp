package com.github.mag0716.sharedelementtransition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = DetailFragmentArgs.fromBundle(arguments)
        val item = argument.item

        if (item != null) {
            detailImage.setBackgroundColor(item.color)
            detailText.text = item.name
            detailImage.transitionName = item.name
        }
    }
}