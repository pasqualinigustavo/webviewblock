package com.webviewblock.presentation.search


import android.os.Bundle
import android.view.*
import android.webkit.URLUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.webviewblock.R
import com.webviewblock.databinding.FragmentSearchBinding
import com.webviewblock.presentation.BaseFragment

class SearchFragment : BaseFragment<SearchViewModel>() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setToolbarTitle(getString(R.string.app_name))
    }

    override fun setupObservers() {

        viewModel.onLoaded.observe(
            viewLifecycleOwner,
            Observer { block ->
                binding.fragmentSearchWebView.blockImages(block ?: false)
            }
        )

        binding.fragmentSearchWebView.setup(
            actionUpdate = { url ->
                url?.let {
                    binding.fragmentSearchEdittextUrl.setText(it)
                }
            },
            actionError = {
                //do some stuff if you get an error
            })

        binding.fragmentSearchOpenPage.setOnClickListener {
            if (validData()) {
                binding.fragmentSearchWebView.navigateToUrl(binding.fragmentSearchEdittextUrl.text.toString())
                //add to history
                viewModel.addToHistory(binding.fragmentSearchEdittextUrl.text.toString())
            }
        }

    }

    private fun validData(): Boolean {
        binding.fragmentSearchTextInputUrl.error = null
        var url = validateInputEditEmpty(
            binding.fragmentSearchTextInputUrl,
            binding.fragmentSearchEdittextUrl
        )
        if (url) {
            url = URLUtil.isValidUrl(binding.fragmentSearchEdittextUrl.text.toString().trim())
            if (!url)
                binding.fragmentSearchTextInputUrl.error = getString(R.string.msg_field_incorrect)
        }
        return url
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var view = menu?.findItem(R.id.menu_settings)
        view?.setOnMenuItemClickListener {
            viewModel.openSettingsPage()
            true
        }
    }

    override fun createViewModel(): SearchViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)
    }

}