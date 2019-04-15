package com.radixdlt.android.util

import androidx.annotation.StringDef

const val PREF_SECRET = "secretKey"
const val FAUCET_ADDRESS = "9i8LKZAVF3is4C4Ga75t9PbvXPXFQpZwa9s1Nifc3weXMBVdnmn" // alphanetwork // old: 9he94tVfQGAVr4xoUpG3uJfB2exURExzFV6E7dq4bxUWRbM5Edd
const val URL_REPORT_ISSUE = "https://radixdlt.typeform.com/to/kPFmVy"
const val KEYSTORE_FILE = "keystore.key"

@Retention(AnnotationRetention.SOURCE)
@StringDef(ALPHANET, ALPHANET2)
annotation class Universe

const val ALPHANET = "ALPHANET"
const val ALPHANET2 = "ALPHANET2"

const val IPV4_ADDRESS_PATTERN =
    "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"

const val IPV6_ADDRESS_PATTERN = "^([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4}\$"
