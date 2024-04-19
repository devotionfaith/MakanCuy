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
import coil.load
import coil.transform.CircleCropTransformation
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.databinding.FragmentProfileBinding
import com.devotion.makancuy.presentation.main.MainActivity
import com.devotion.makancuy.presentation.main.MainViewModel
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_tab_profile)
                transformations(CircleCropTransformation())
            }
            binding.nameEditText.setText(it.name)
            binding.usernameEditText.setText(it.username)
            binding.emailEditText.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener {
            doLogout()
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
                        "Register Failed : ${it.exception?.message.orEmpty()}",
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


    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.usernameEditText.isEnabled = it
        }
        viewModel.buttonText.observe(viewLifecycleOwner) {
            binding.btnEdit.text = it
        }
    }
}