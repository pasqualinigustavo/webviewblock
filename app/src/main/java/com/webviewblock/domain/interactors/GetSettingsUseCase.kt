package com.webviewblock.domain.interactors

import com.webviewblock.app.repository.LocalSettingsRepository
import com.webviewblock.domain.History
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSettingsUseCase @Inject constructor(
    var repository: LocalSettingsRepository
) {

    fun execute(): List<History> {
        return repository.getHistory()
    }

    fun blockImagesPreference(): Boolean {
        return repository.blockImagesPreference
    }

    fun setBlockImagesPreference(block: Boolean) {
        repository.blockImagesPreference = block
    }

    fun clear() {
        repository.clear()
    }

    fun updateHistory(historyList: List<History>) {
        repository.updateHistory(historyList)
    }
}
