package com.webviewblock.domain

data class History(
    val time: String,
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        var hist = other as History
        return this.url.equals(hist.url, true)
    }
}
