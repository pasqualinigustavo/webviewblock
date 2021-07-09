package com.webviewblock.util

import javax.inject.Singleton

@Singleton
interface ResProvider {

    fun getString(id: Int): String

    fun getString(id: Int, vararg formatArgs: Any): String

    fun getRawFile(fileId: Int): String

    fun getColor(id: Int): Int
}
