package com.example.empresas.ui


import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.empresas.R
import com.example.empresas.presentation.LoginViewModel
import com.example.empresas.presentation.ViewState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val loginViewModel by viewModel<LoginViewModel>()

    lateinit var button: AppCompatButton
    lateinit var viewLoading: View
    lateinit var progressBar: ProgressBar
    lateinit var edtEmail: TextInputEditText
    lateinit var edtPassword: TextInputEditText
    lateinit var ilPassword: TextInputLayout
    lateinit var ilEmail: TextInputLayout
    lateinit var txtAlert: TextView
    lateinit var loadingGroup: Group

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.btnLogin)
        viewLoading = view.findViewById(R.id.viewLoading)
        progressBar = view.findViewById(R.id.pbLoading)
        edtEmail = view.findViewById(R.id.edtEmail)
        ilEmail = view.findViewById(R.id.ilEmail)
        ilPassword = view.findViewById(R.id.ilPassword)
        edtPassword = view.findViewById(R.id.edtPassword)
        loadingGroup = view.findViewById(R.id.gpLoading)
        txtAlert = view.findViewById(R.id.txtAlert)

        ilEmail.editText?.setText("testeapple@ioasys.com.br")
        ilPassword.editText?.setText("12341234")

        button.setOnClickListener {
            loginViewModel.login(
                ilEmail.editText?.text.toString(),
                ilPassword.editText?.text.toString()
            )
        }

        genericChangeTextListener(edtPassword)
        genericChangeTextListener(edtEmail)

        setObservers()
    }

    private fun genericChangeTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (loginViewModel.btnLoginState.value == false) {
                    loginViewModel.clearFieldPasswordErrors()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setObservers() {
        loginViewModel.headerLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState.state) {
                ViewState.State.SUCCESS -> onResultSuccess()
                ViewState.State.ERROR -> onResultError(viewState.error)
                ViewState.State.LOADING -> onLoading(viewState.isLoading)
            }
        }

        loginViewModel.emailFieldError.observe(viewLifecycleOwner) { stringResId ->
            if (stringResId != null) {
                edtEmail.setError(stringResId)
                ilEmail.setError(stringResId)
            }
        }

        loginViewModel.passwordFieldError.observe(viewLifecycleOwner) { stringResId ->
            if (stringResId != null) {
                edtPassword.setError(stringResId)
                ilPassword.setError(stringResId)
            }
        }

        loginViewModel.btnLoginState.observe(viewLifecycleOwner) { status ->
            btnLogin.isEnabled = status
        }
    }

    private fun onLoading(loading: Boolean) {
        if (loading)
            loadingGroup.gpLoading.visibility = View.VISIBLE
        else
            loadingGroup.gpLoading.visibility = View.GONE
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        if (stringResId != null) {
            isEndIconVisible = false
            val errorColor = ContextCompat.getColor(context, R.color.neon_red)
            val fgcspan = ForegroundColorSpan(errorColor)
            val builder = SpannableStringBuilder(" ")
            builder.setSpan(fgcspan, 0, "".length, 0)
            error = builder
        } else {
            isEndIconVisible = true;
        }
    }

    private fun EditText.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

    private fun onResultError(error: Throwable?) {
        txtAlert.visibility = View.VISIBLE
        Toast.makeText(requireContext(), "${error?.message.toString()}", Toast.LENGTH_LONG).show()
    }

    private fun onResultSuccess() {
        findNavController().navigate(
            LoginFragmentDirections
                .actionLoginFragmentToHomeFragment()

        )
    }
}