package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_count.*

// TODO: CountFragment をスタックに追加していく
// タブ毎にスタックを管理するので、parent の Fragment 側で childFragmentManager を利用する
// CountFragment が複数スタックに積まれていても、up key で parent Fragment に戻る
class CountFragment : Fragment() {

    companion object {
        const val KEY = "count"
        fun newInstance(value: Int = 0): CountFragment {
            return CountFragment().apply {
                arguments = bundleOf(KEY to value)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_count, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = arguments?.getString(KEY)
        button.setOnClickListener {
        }
    }
}