package com.example.nikestore.view.Activity

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.nikestore.R
import com.example.nikestore.common.NikeActivity
import com.example.nikestore.common.TokenContainer
import com.example.nikestore.common.convertDpToPixel
import com.example.nikestore.common.setupWithNavController
import com.example.nikestore.viewmodel.MainViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.sevenlearn.nikestore.data.CartItemCount
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : NikeActivity() {
    private var currentNavController: LiveData<NavController>? = null
    val mainViewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationMain)

        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount){
        val badge=bottomNavigationMain.getOrCreateBadge(R.id.cart)
        badge.badgeGravity=BadgeDrawable.TOP_START
        badge.backgroundColor=MaterialColors.getColor(bottomNavigationMain,R.attr.colorPrimary)
        badge.number=cartItemCount.count
        badge.verticalOffset= convertDpToPixel(10f,this).toInt()
        if(!TokenContainer.token.isNullOrEmpty() && cartItemCount.count > 0){
            badge.isVisible
        }
        //badge.isVisible = cartItemCount.count > 0
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getCartItemCount()

    }
}