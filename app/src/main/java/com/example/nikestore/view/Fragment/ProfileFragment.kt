package com.example.nikestore.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikestore.R
import com.example.nikestore.common.NikeFragment
import com.example.nikestore.view.Activity.AuthActivity
import com.example.nikestore.view.Activity.FavoriteActivity
import com.example.nikestore.view.Activity.OrderHistoryActivity
import com.example.nikestore.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment:NikeFragment() {

    val profileViewModel:ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteActivity::class.java))
        }
        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState(){
        if (profileViewModel.isSignedIn){

            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
            usernameTv.text = profileViewModel.username
            authBtn.setOnClickListener {
                profileViewModel.signOut()
                checkAuthState()
            }

        }else{

            authBtn.text = getString(R.string.signIn)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
            usernameTv.text = getString(R.string.guest_user)

        }
    }
}