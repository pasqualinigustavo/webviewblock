package com.webviewblock.domain.interactors;

import com.webviewblock.app.repository.LocalSettingsRepository;
import com.webviewblock.domain.History;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetSettingsUseCase {

    LocalSettingsRepository repository;

    @Inject
    public GetSettingsUseCase(LocalSettingsRepository repository) {
        this.repository = repository;
    }

    public List<History> execute() {
        return repository.getHistory();
    }

    public boolean isBlockImagesPreference() {
        return repository.isBlockImagesPreference();
    }

    public void setBlockImagesPreference(Boolean block) {
        repository.setBlockImagesPreference(block);
    }

    public void clear() {
        repository.clear();
    }

    public void updateHistory(List<History> historyList) {
        repository.updateHistory(historyList);
    }
}
