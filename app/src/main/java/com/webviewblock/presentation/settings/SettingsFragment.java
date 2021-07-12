package com.webviewblock.presentation.settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.webviewblock.R;
import com.webviewblock.databinding.FragmentSettingsBinding;
import com.webviewblock.presentation.BaseFragment;
import com.webviewblock.presentation.BaseViewModel;
import com.webviewblock.presentation.settings.adapter.HistoryAdapter;

public class SettingsFragment extends BaseFragment {

    private HistoryAdapter adapter = null;
    private SettingsViewModel viewModel = null;
    private FragmentSettingsBinding _binding = null;

    @NonNull
    @Override
    public BaseViewModel createViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);
        return viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return _binding.getRoot();
    }

    @Override
    protected void setupObservers() {
        setupRecyclerView();
        viewModel.getOnLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean block) {
                ((HistoryAdapter) _binding.fragmentSettingsRecyclerviewHistory.getAdapter()).setItems(viewModel.historyList());
                _binding.fragmentSettingsSwitchBlock.setChecked(block);
            }
        });

        _binding.fragmentSettingsSwitchBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.blockImages(isChecked);
            }
        });

        _binding.fragmentSettingsButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.clear();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(false);
        setToolbarTitle(getString(R.string.app_name));
    }

    private void setupRecyclerView() {
        adapter = new HistoryAdapter();
        adapter.setup(viewModel.historyList(), url -> {
            Toast.makeText(requireContext(), url, Toast.LENGTH_SHORT).show();
            //viewModel.visitWebSite();
        });
        _binding.fragmentSettingsRecyclerviewHistory.setAdapter(adapter);
        _binding.fragmentSettingsRecyclerviewHistory.setHasFixedSize(true);
    }
}