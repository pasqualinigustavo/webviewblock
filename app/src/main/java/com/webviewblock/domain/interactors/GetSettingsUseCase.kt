package com.webviewblock.domain.interactors

import com.webviewblock.app.repository.LocalSettingsRepository
import com.webviewblock.domain.History
import com.webviewblock.util.BehaviourSubjectDecorator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSettingsUseCase @Inject constructor(
    var repository: LocalSettingsRepository
) {

    fun execute(): BehaviourSubjectDecorator<List<History>> {
        return repository.history
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
}
