package com.github.mag0716.nestednavigation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onResume() {
        super.onResume()
        // TODO: 未ログインの場合に LoginFragment へ遷移させたい
        // java.lang.IllegalArgumentException: navigation destination com.github.mag0716.nestednavigation:id/action_login is unknown to this NavController
        findNavController().navigate(R.id.action_login)
    }
}
