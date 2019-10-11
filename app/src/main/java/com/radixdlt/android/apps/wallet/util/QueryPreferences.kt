package com.radixdlt.android.apps.wallet.util

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager

object QueryPreferences {
    private const val PREF_ADDRESS = "address"
    private const val PREF_PASSWORD = "password"
    private const val PREF_AUTOLOCK_TIMEOUT = "autolock_timeout"
    private const val PREF_NETWORK = "network"
    private const val PREF_RANDOM_SELECTION = "random_selection"
    private const val PREF_NODE_IP = "node_ip"
    private const val PREF_MNEMONIC_SEED = "mnemonic_seed"
    private const val PREF_FAUCET = "faucet"
    private const val PREF_TUTORIAL_RECEIVE = "tutorial_receive"
    private const val PREF_TERMS_ACCEPTED = "terms_accepted"
    private const val PREF_WALLET_BACKED_UP = "wallet_backed_up"

    fun getPrefAddress(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_ADDRESS, "")!!
    }

    @SuppressLint("ApplySharedPref")
    fun setPrefAddress(context: Context, address: String) {
        // Using commit instead of apply as it happens async already and
        // we want it saved immediately.
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_ADDRESS, address)
                .commit()
    }

    fun getPrefPasswordEnabled(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_PASSWORD, true)
    }

    fun setPrefPasswordEnabled(context: Context, enabled: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_PASSWORD, enabled)
                .apply()
    }

    fun getPrefAutoLockTimeOut(context: Context): Long {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(PREF_AUTOLOCK_TIMEOUT, 2000)
    }

    fun setPrefAutoLockTimeOut(context: Context, timeout: Long) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(PREF_AUTOLOCK_TIMEOUT, timeout)
                .apply()
    }

    @Universe
    fun getPrefNetwork(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(
                PREF_NETWORK,
                BETANET
            )
    }

    @SuppressLint("ApplySharedPref")
    fun setPrefNetwork(context: Context, @Universe network: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_NETWORK, network)
            .commit()
    }

    fun getPrefCreatedByMnemonicOrSeed(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_MNEMONIC_SEED, false)
    }

    fun setPrefCreatedByMnemonicOrSeed(context: Context, created: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_MNEMONIC_SEED, created)
            .apply()
    }

    /**
     * Node selection boolean flag to determine whether node selection
     * is random or custom
     *
     * @param context
     * @return true if node selection is random
     * */
    fun getPrefIsRandomNodeSelection(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_RANDOM_SELECTION, true)
    }

    /**
     * Sets whether node selection should be random or custom
     *
     * @param context
     * @param random
     * */
    @SuppressLint("ApplySharedPref")
    fun setPrefRandomNodeSelection(context: Context, random: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_RANDOM_SELECTION, random)
            .commit()
    }

    fun getPrefNodeIP(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(PREF_NODE_IP, "")!!
    }

    @SuppressLint("ApplySharedPref")
    fun setPrefNodeIP(context: Context, network: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_NODE_IP, network)
            .commit()
    }

    fun setRemoteFaucet(context: Context, created: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_FAUCET, created)
            .apply()
    }

    /**
     * Check if using remote faucet
     *
     * @param context
     * @return true if node selection is random
     * */
    fun isRemoteFaucet(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_FAUCET, true)
    }

    fun isTutorialReceiveShown(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_TUTORIAL_RECEIVE, false)
    }

    fun setPrefTutorialReceiveShown(context: Context, shown: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_TUTORIAL_RECEIVE, shown)
            .apply()
    }

    fun isTermsAccepted(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_TERMS_ACCEPTED, false)
    }

    fun setPrefTermsAccepted(context: Context, accepted: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_TERMS_ACCEPTED, accepted)
            .apply()
    }

    fun isWalletBackedUp(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_WALLET_BACKED_UP, false)
    }

    fun setPrefWalletBackedUp(context: Context, accepted: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_WALLET_BACKED_UP, accepted)
            .apply()
    }
}
