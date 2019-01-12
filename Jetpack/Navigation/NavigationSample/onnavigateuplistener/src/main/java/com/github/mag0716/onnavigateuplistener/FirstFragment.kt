package com.github.mag0716.onnavigateuplistener


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            NavigationUI.navigateUp(findNavController(),
                    // navigateUp の動作をカスタムするために AppBarConfiguration に NavigateUpListener を設定する
                    AppBarConfiguration.Builder()
                            .setFallbackOnNavigateUpListener {
                                Log.d(MainActivity.TAG, "onNavigateUp")
                                // navigateUp を成功と扱う場合に true を返す
                                true
                            }
                            .build()
            )
        }
    }
}
