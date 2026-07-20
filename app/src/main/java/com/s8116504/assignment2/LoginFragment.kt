package com.s8116504.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.s8116504.assignment2.ui.login.LoginState
import com.s8116504.assignment2.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private fun openToSDocument() {
        try {
            val assetManager = requireContext().assets
            val inputStream = assetManager.open("terms_of_service.pdf")

            // Copy to cache so it can be shared/opened
            val file = java.io.File(requireContext().cacheDir, "terms_of_service.pdf")
            val outputStream = java.io.FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            // Open with FileProvider
            val uri = androidx.core.content.FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        } catch (e: Exception) {
            android.widget.Toast.makeText(
                requireContext(),
                "Could not open document",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnEnterStudio)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val tvError = view.findViewById<TextView>(R.id.tvError)
        val ivPasswordToggle = view.findViewById<ImageView>(R.id.ivPasswordToggle)
        val cbToS = view.findViewById<android.widget.CheckBox>(R.id.cbToS)
        val tvToSLink = view.findViewById<TextView>(R.id.tvToSLink)

        var isPasswordVisible = false

        ivPasswordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Show password
                etPassword.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivPasswordToggle.setImageResource(R.drawable.show_ic)
            } else {
                // Hide password
                etPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivPasswordToggle.setImageResource(R.drawable.hide_ic)
            }
            // Keep cursor at end
            etPassword.setSelection(etPassword.text.length)
        }

// Open/download ToS when link tapped
        tvToSLink.setOnClickListener {
            openToSDocument()
        }
        tvToSLink.paintFlags = tvToSLink.paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG
// Prevent login if ToS not checked
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                tvError.visibility = View.VISIBLE
                tvError.text = "Please enter your name and student ID"
                return@setOnClickListener
            }

            if (!cbToS.isChecked) {
                tvError.visibility = View.VISIBLE
                tvError.text = "Please agree to the Terms of Service"
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(
                androidx.lifecycle.Lifecycle.State.STARTED
            ) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Idle -> {
                            progressBar.visibility = View.GONE
                            btnLogin.isEnabled = true
                        }
                        is LoginState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            btnLogin.isEnabled = false
                            tvError.visibility = View.GONE
                        }
                        is LoginState.Success -> {
                            progressBar.visibility = View.GONE
                            val bundle = Bundle()
                            bundle.putString("keypass", state.keypass)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_dashboardFragment,
                                bundle
                            )
                        }
                        is LoginState.Error -> {
                            progressBar.visibility = View.GONE
                            btnLogin.isEnabled = true
                            tvError.visibility = View.VISIBLE
                            tvError.text = state.message
                        }
                    }
                }
            }
        }
    }
}