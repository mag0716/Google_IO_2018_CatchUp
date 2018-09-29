package com.github.mag0716.bottomsheetdialog

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_top.*

class TopFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            BottomSheetNavigationView().show(childFragmentManager, BottomSheetNavigationView::class.java.canonicalName)
        }
    }

    class BottomSheetNavigationView : BottomSheetDialogFragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val navigationView = inflater.inflate(R.layout.view_bottom_sheet, container, false) as NavigationView

            return navigationView
        }
    }
}