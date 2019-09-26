package com.radixdlt.android.apps.wallet.fragment

import android.Manifest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.radixdlt.android.R
import com.radixdlt.android.apps.wallet.helper.DelayHelper
import com.radixdlt.android.apps.wallet.helper.clickOn
import com.radixdlt.android.apps.wallet.helper.navigationIconMatcher
import com.radixdlt.android.apps.wallet.ui.activity.NewWalletActivity
import com.radixdlt.android.apps.wallet.util.copyToClipboard
import com.schibsted.spain.barista.assertion.BaristaErrorAssertions.assertError
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.PermissionGranter
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import com.schibsted.spain.barista.rule.cleardata.ClearFilesRule
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class PaymentInputFragmentTest {

    @get:Rule
    var newWalletActivityTestRule: ActivityTestRule<NewWalletActivity> =
        ActivityTestRule(NewWalletActivity::class.java)

    // Clear all app's SharedPreferences
    @get:Rule
    var clearPreferencesRule = ClearPreferencesRule()

    // Delete all tables from all the app's SQLite Databases
    @get:Rule
    var clearDatabaseRule = ClearDatabaseRule()

    // Delete all files in getFilesDir() and getCacheDir()
    @get:Rule
    var clearFilesRule = ClearFilesRule()

    @Test
    fun testNoteInputIsDisplayedWhenAddNoteIsClicked() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        clickOn(R.string.payment_input_fragment_note_optional)
        assertDisplayed(R.id.paymentInputMessageTIL)
        assertDisplayed(R.string.payment_input_fragment_note_delete)
    }

    @Test
    fun testNoteInputIsHiddenWhenDeleteNoteIsClicked() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        clickOn(R.string.payment_input_fragment_note_optional)
        assertDisplayed(R.id.paymentInputMessageTIL)
        clickOn(R.string.payment_input_fragment_note_delete)
        assertNotDisplayed(R.id.paymentInputMessageTIL)
        assertDisplayed(R.string.payment_input_fragment_note_optional)
    }

    @Test
    fun testMaxValueButtonPopulatesInputField() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        clickOn(R.id.paymentInputMaxValue)
        assertNotDisplayed("")
    }

    @Test
    fun testAssetSelectionButtonOpensNewScreen() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        clickOn(R.id.paymentInputLinearLayout)
        assertDisplayed(R.id.toolbar_search)
    }

    @Test
    fun testNotARadixAddressShowsError() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        writeTo(R.id.paymentInputAddressTIET, "hello")
        assertDisplayed(R.string.payment_input_fragment_enter_valid_address_error)
    }

    @Test
    fun testMoreThanMaxValueShowsError() {
        createWallet()

        navigateToPayScreen()
        inputPaymentDetails()

        writeTo(R.id.paymentInputAmountEditText, "100")
        assertError(R.id.paymentInputAmountTIL, R.string.payment_input_fragment_not_enough_tokens_error)
    }

    @Test
    fun testClickingSendWithEmptyFieldsShowErrors() {
        createWallet()

        navigateToPayScreen()

        clickOn(R.id.paymentInputSendButton)

        assertError(R.id.paymentInputAddressTIL, R.string.payment_input_fragment_address_error)
        assertError(R.id.paymentInputAmountTIL, R.string.payment_input_fragment_amount_error)
    }

    @Test
    fun testPasteButtonAfterCopyingRadixAddressToClipBoard() {
        createWallet()

        navigateToPayScreen()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val radixAddress = "9g8ixQr2RdJ1y9cSo3zga1H5sgV9Z359Rq6gv3yMoraqtFtaWdm"

        // Makes sure that copying happens on the correct thread when testing
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            copyToClipboard(context, radixAddress)
        }

        clickOn(R.id.paymentInputPasteButton)
        assertDisplayed(R.id.paymentInputAddressTIET, radixAddress)
    }

    @Test
    fun testPasteButtonAfterCopyingNotAnAddressToClipBoard() {
        createWallet()

        navigateToPayScreen()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val notAnAddress = "Hello"

        // Makes sure that copying happens on the correct thread when testing
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            copyToClipboard(context, notAnAddress)
        }

        clickOn(R.id.paymentInputPasteButton)
        assertDisplayed(R.id.paymentInputAddressTIET, notAnAddress)
        assertError(R.id.paymentInputAddressTIL, R.string.payment_input_fragment_enter_valid_address_error)
    }

    @Test
    fun testQrButtonOpensCamera() {
        createWallet()

        navigateToPayScreen()

        clickOn(R.id.paymentInputQrScanButton)

        PermissionGranter.allowPermissionsIfNeeded(Manifest.permission.CAMERA)

        assertDisplayed(R.string.barcode_capture_activity_title)
    }

    private fun createWallet() {
        clickOn(R.id.importWalletFromMnemonicButton)
        writeTo(R.id.inputMnemonicOrSeedTIET, "instrumentationtest")
        clickOn(R.id.createWalletFromMnemonicButton)
    }

    private fun navigateToPayScreen() {
        // Click on x on the toolbar to dismiss
        clickOn(navigationIconMatcher())
        assertDisplayed(R.id.toolbar_search)
        DelayHelper.waitTime(TimeUnit.SECONDS.toMillis(30))
        clickOn(R.id.payButton)
    }

    private fun inputPaymentDetails() {
        writeTo(R.id.paymentInputAddressTIET, ADDRESS_TO)
        writeTo(R.id.paymentInputAmountEditText, AMOUNT)
    }

    companion object {
        const val ADDRESS_TO = "9iNGvjXbifbkpPy2252tv8w8QCWnTkixxB1YwrYz1c2AR5xG8VJ"
        const val AMOUNT = "42.00"
    }
}