package com.webviewblock.presentation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.webviewblock.R;
import com.webviewblock.components.WebViewCustom;
import com.webviewblock.databinding.FragmentSearchBinding;
import com.webviewblock.presentation.BaseFragment;
import com.webviewblock.presentation.BaseViewModel;

public class SearchFragment extends BaseFragment {

    private SearchViewModel viewModel = null;
    private FragmentSearchBinding _binding = null;

    @NonNull
    @Override
    public BaseViewModel createViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SearchViewModel.class);
        return viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _binding = FragmentSearchBinding.inflate(inflater, container, false);
        return _binding.getRoot();
    }

    @Override
    protected void setupObservers() {
        viewModel.getOnLoaded().observe(getViewLifecycleOwner(), block ->
                _binding.fragmentSearchWebView.setup(block, new WebViewCustom.WebviewListener() {
                    @Override
                    public void actionUpdate(String url) {
                        _binding.fragmentSearchEdittextUrl.setText(url);
                    }

                    @Override
                    public void actionError() {
                        //do some stuff if you get an error
                    }
                }));

        _binding.fragmentSearchOpenPage.setOnClickListener(v -> {
            if (validData()) {
                _binding.fragmentSearchWebView.navigateToUrl(_binding.fragmentSearchEdittextUrl.getText().toString());
                //add to history
                viewModel.addToHistory(_binding.fragmentSearchEdittextUrl.getText().toString());
            }
        });

        _binding.fragmentSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _binding.fragmentSearchWebView.tryToBack();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setToolbarTitle(getString(R.string.app_name));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem view = menu.findItem(R.id.menu_settings);
        view.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.openSettingsPage();
                return false;
            }
        });
    }

    private boolean validData() {
        _binding.fragmentSearchTextInputUrl.setError(null);
        _binding.fragmentSearchTextInputUrl.setErrorEnabled(true);
        boolean url = validateInputEditEmpty(
                _binding.fragmentSearchTextInputUrl,
                _binding.fragmentSearchEdittextUrl
        );
        if (url) {
            url = URLUtil.isValidUrl(_binding.fragmentSearchEdittextUrl.getText().toString().trim());
            if (!url)
                _binding.fragmentSearchTextInputUrl.setError(getString(R.string.msg_field_incorrect));
        } else _binding.fragmentSearchTextInputUrl.setError(getString(R.string.msg_field_incorrect));
        return url;
    }
}