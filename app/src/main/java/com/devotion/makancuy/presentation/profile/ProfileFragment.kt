package com.devotion.makancuy.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.databinding.FragmentProfileBinding
import com.devotion.makancuy.presentation.main.MainActivity
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels(){
        val s : FirebaseService = FirebaseServiceImpl()
        val ds : AuthDataSource = FirebaseAuthDataSource(s)
        val r : UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(ProfileViewModel(r))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.getCurrentUser().let {
            binding.ivProfile.setImageResource(R.drawable.ic_profile)
            binding.nameEditText.setText(it?.fullName.orEmpty())
            binding.emailEditText.setText(it?.email.orEmpty())
        }
    }

    private fun setClickListener() {
        binding.btnEditUsername.setOnClickListener {
            binding.nameInputLayout.isEnabled = true
            binding.emailInputLayout.isVisible = false
            binding.btnSaveUsername.isVisible = true
            binding.btnSaveEmail.isVisible = false
            binding.btnEditUsername.isVisible = false
            binding.btnEditEmail.isVisible = false
        }
        binding.btnEditEmail.setOnClickListener {
            binding.nameInputLayout.isVisible = false
            binding.emailInputLayout.isEnabled = true
            binding.btnSaveUsername.isVisible = false
            binding.btnSaveEmail.isVisible = true
            binding.btnEditEmail.isVisible = false
            binding.btnEditUsername.isVisible = false
        }
        binding.btnSaveUsername.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            updateProfile(username)
        }
        binding.btnSaveEmail.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            updateEmail(email)
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun updateEmail(email : String){
        viewModel.updateEmail(email).observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_success_notif),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                },
                doOnError = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Update Email Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Update Email ", "Update Email  Failed : ${it.exception?.message.orEmpty()}", it.exception)
                    binding.btnSaveEmail.isVisible = true
                    binding.btnEditEmail.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoadingSave.isVisible  = true
                    binding.btnSaveEmail.isVisible = false
                    binding.btnEditEmail.isVisible = false
                }
            )
        }
    }

    private fun updateProfile(username: String) {
        viewModel.updateUsername(username).observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_success_notif),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                },
                doOnError = {
                    binding.pbLoadingSave.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Update Username Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSaveUsername.isVisible = true
                    binding.btnEditUsername.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoadingSave.isVisible  = true
                    binding.btnSaveUsername.isVisible = false
                    binding.btnEditUsername.isVisible = false
                }
            )
        }

    }

    private fun doLogout() {
        viewModel.doLogout().observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogout.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogout.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        "Logout Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("LogoutFailed", "Logout Failed : ${it.exception?.message.orEmpty()}", it.exception)

                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogout.isVisible = false
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}