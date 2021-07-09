package com.webviewblock.app

interface SharedPreferenceProvider {
    fun getString(key: String, defValue: String): String
    fun putString(key: String, value: String)
    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
    fun getInt(key: String, defValue: Int): Int
    fun putInt(key: String, value: Int)
    fun putLong(key: String, value: Long)
    fun getLong(key: String, defValue: Long): Long
    fun clearAll()
}