package com.devotion.makancuy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.devotion.makancuy.R
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.FragmentHomeBinding
import com.devotion.makancuy.presentation.detailmenu.DetailMenuActivity
import com.devotion.makancuy.presentation.home.adapter.CategoryAdapter
import com.devotion.makancuy.presentation.home.adapter.MenuAdapter
import com.devotion.makancuy.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.name)
        }
    }
    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(viewModel.getListMode()) {
            DetailMenuActivity.startActivity(
                requireContext(),
                it,
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentHomeBinding.inflate(
                layoutInflater,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        applyGridMode()
        setupCategory()
        getCategoryData()
        getMenuData(null)
        setClickAction()
        setupUsername()
    }

    private fun setupUsername() {
        viewModel.getCurrentUsername().let { username ->
            if (username.isNullOrEmpty()) {
                binding.layoutHeader.tvName.text = getString(R.string.default_username)
            } else {
                binding.layoutHeader.tvName.text = getString(R.string.text_name, username)
            }
        }
    }

    private fun getMenuData(categoryName: String? = null) {
        viewModel.getMenu(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                    binding.pbLoadingMenu.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_error_category),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingMenu.isVisible = true
                },
            )
        }
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun setupMenu() {
        binding.rvMenu.apply {
            adapter = menuAdapter
        }
    }

    private fun getCategoryData() {
        viewModel.getCategory().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                    binding.pbLoadingCategory.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_error_menu),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingCategory.isVisible = true
                },
            )
        }
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitDataCategory(data)
    }

    private fun setupCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun applyGridMode() {
        val isUsingGridMode = viewModel.isUsingGridMode()
        val listMode = if (isUsingGridMode) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        setIcon(isUsingGridMode)
        menuAdapter.updateListMode(listMode)
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGridMode) 2 else 1)
            adapter = menuAdapter
        }
    }

    private fun setClickAction() {
        binding.ivChangeLayout.setOnClickListener {
            viewModel.changeListMode()
            applyGridMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.ivChangeLayout.setImageResource(if (usingGridMode) R.drawable.ic_list else R.drawable.ic_grid)
    }
}
