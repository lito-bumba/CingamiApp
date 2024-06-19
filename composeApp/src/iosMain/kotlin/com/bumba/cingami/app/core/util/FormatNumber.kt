package com.bumba.cingami.app.core.util

import platform.Foundation.NSString
import platform.Foundation.stringByAppendingFormat

actual fun Double.formatNumber(): String {
    return NSString().stringByAppendingFormat(format = "%.2f", this)
}