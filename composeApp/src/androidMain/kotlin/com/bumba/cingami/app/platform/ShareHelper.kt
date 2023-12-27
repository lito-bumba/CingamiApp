package com.bumba.cingami.app.platform

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class ShareHelper(
    private val context: Context
) {

    actual suspend fun share(text: String) {
        withContext(Dispatchers.IO) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    text
                )
                type = "text/pain"
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }
}