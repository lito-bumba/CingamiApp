package com.bumba.cingami.app.core.platform

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow

actual class ShareHelper {
    actual suspend fun share(text: String) {
        val window = UIApplication.sharedApplication.windows.last() as? UIWindow
        val currentViewController = window?.rootViewController
        val activityViewController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )
        currentViewController?.presentViewController(
            viewControllerToPresent = activityViewController,
            animated = true,
            completion = null,
        )
    }
}