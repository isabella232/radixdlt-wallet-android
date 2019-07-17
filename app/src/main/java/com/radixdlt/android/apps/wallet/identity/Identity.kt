package com.radixdlt.android.apps.wallet.identity

import com.radixdlt.client.application.RadixApplicationAPI
import com.radixdlt.client.application.identity.RadixIdentity

/**
 * We use a simple object acting as a Singleton in Kotlin to keep a reference to
 * our identity during the lifecycle of the application.
 * */
object Identity {
    var myIdentity: RadixIdentity? = null

    var api: RadixApplicationAPI? = null
        get() = field?.let {
            return it
        } ?: run {
            field = myIdentity?.let {
                RadixApplicationAPI.create(
                    BootStrapConfigAndroidImpl.address("10.97.7.221"),
                    it
                ) // BootStrapConfigAndroidImpl.address("81.235.48.50") // 5.71.214.26
            }
            field?.pull()
            return field
        }
        private set

    fun clear() {
        myIdentity = null
        api = null
    }
}
