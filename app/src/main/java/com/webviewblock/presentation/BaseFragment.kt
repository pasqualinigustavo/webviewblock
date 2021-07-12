package com.webviewblock.presentation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.webviewblock.R
import com.webviewblock.di.Injectable
import com.webviewblock.domain.interfaces.ActivityToolbarBehaviour
import com.webviewblock.navigator.NavigationController
import javax.inject.Inject

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment(), Injectable {

    var activityToolbarBehaviour: ActivityToolbarBehaviour? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity is ActivityToolbarBehaviour) {
            this.activityToolbarBehaviour = activity as ActivityToolbarBehaviour?
        }
    }

    protected fun setToolbarTitle(title: String) {
        this.activityToolbarBehaviour?.setToolbarTitle(title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = createViewModel()
        setupObservers()
        navController()?.let {
            viewModel.navigator.observeEvents(this, it)
        }
        viewModel.onAttached()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val isThereSomethingToPop = navController()?.finish() ?: false
                    if (!isThereSomethingToPop) {
                        activity?.finish()
                    }
                }
            }
        )
    }

    fun navController(): NavigationController? {
        try {
            findNavController()?.let {
                return NavigationController(
                    it,
                    requireContext()
                )
            }
        } catch (e: Exception) {

        }
        return null
    }

    fun validateInputEditEmpty(textInputLayout: TextInputLayout?, editText: EditText?): Boolean {
        if (!editText?.text.toString().isNullOrEmpty())
            textInputLayout?.isErrorEnabled = false
        else {
            textInputLayout?.error = getString(R.string.msg_field_required)
            return false
        }
        return true
    }

    abstract fun createViewModel(): ViewModel

    protected abstract fun setupObservers()
}
