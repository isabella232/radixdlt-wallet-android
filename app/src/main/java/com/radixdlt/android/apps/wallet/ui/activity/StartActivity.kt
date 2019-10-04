package com.radixdlt.android.apps.wallet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.radixdlt.android.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_start).navigateUp()

    override fun onBackPressed() {
        val createWallet = findNavController(R.id.nav_host_start).currentDestination?.id
        if (createWallet == R.id.navigation_create_wallet) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
