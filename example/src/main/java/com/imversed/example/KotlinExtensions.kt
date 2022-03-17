package com.imversed.example

import android.util.Log

inline fun <R> safe(tag: String, block: () -> R?): R? {
    return try {
        block.invoke()
    } catch (ex: Exception) {
        Log.e(tag, "", ex)
        null
    }
}

inline fun <T> T?.or(block: () -> T) = this ?: block.invoke()
