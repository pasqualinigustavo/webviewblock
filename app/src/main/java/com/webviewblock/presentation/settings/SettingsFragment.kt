package com.webviewblock.presentation.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.webviewblock.R
import com.webviewblock.databinding.FragmentSettingsBinding
import com.webviewblock.presentation.BaseFragment
import com.webviewblock.presentation.settings.adapter.HistoryAdapter

class SettingsFragment : BaseFragment<SettingsViewModel>() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var adapter: HistoryAdapter? = null

    companion object {
        val TAG = SettingsFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        setToolbarTitle(getString(R.string.app_name))
    }

    override fun setupObservers() {
        setupRecyclerView()
        viewModel.onLoaded.observe(
            viewLifecycleOwner,
            Observer { loaded ->
                    (binding.fragmentSettingsRecyclerviewHistory.adapter as? HistoryAdapter)?.items =
                            viewModel.historyList().toMutableList()
            }
        )

        binding.fragmentSettingsSwitchBlock.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.blockImages(isChecked)
        }

        binding.fragmentSettingsButtonClear.setOnClickListener {
            viewModel.clear()
        }
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(
            clickListener = { item ->
                Toast.makeText(requireContext(), item.url, Toast.LENGTH_SHORT).show()
                //viewModel.visitWebSite()
            }
        )
        binding.fragmentSettingsRecyclerviewHistory.adapter = adapter

        if (viewModel.historyList().isNotEmpty())
            adapter?.setup(viewModel.historyList())

        binding.fragmentSettingsRecyclerviewHistory.setHasFixedSize(true)
    }

    override fun createViewModel(): SettingsViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(SettingsViewModel::class.java)
    }

}