package com.devotion.makancuy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.datasource.category.CategoryApiDataSource
import com.devotion.makancuy.data.datasource.menu.MenuApiDataSource
import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSource
import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSourceImpl
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.CategoryRepositoryImpl
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.MenuRepositoryImpl
import com.devotion.makancuy.data.repository.UserPreferenceRepository
import com.devotion.makancuy.data.repository.UserPreferenceRepositoryImpl
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.local.pref.UserPreference
import com.devotion.makancuy.data.source.local.pref.UserPreferenceImpl
import com.devotion.makancuy.data.source.network.service.RestaurantApiService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.databinding.FragmentHomeBinding
import com.devotion.makancuy.presentation.detailmenu.DetailMenuActivity
import com.devotion.makancuy.presentation.home.adapter.CategoryAdapter
import com.devotion.makancuy.presentation.home.adapter.MenuAdapter
import com.devotion.makancuy.presentation.main.MainViewModel
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = RestaurantApiService.invoke()
        val userPreference: UserPreference = UserPreferenceImpl(requireContext())
        val userPrefDataSource: UserPreferenceDataSource = UserPreferenceDataSourceImpl(userPreference)
        val userPreferenceRepository: UserPreferenceRepository = UserPreferenceRepositoryImpl(userPrefDataSource)
        val categoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                menuRepository,
                userPreferenceRepository
            )
        )
    }

    private val mainViewModel : MainViewModel by activityViewModels{
        val s : FirebaseService = FirebaseServiceImpl()
        val ds : AuthDataSource = FirebaseAuthDataSource(s)
        val r : UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(r))
    }
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.name)
        }
    }

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(viewModel.getListMode()) {
            DetailMenuActivity.startActivity(
                requireContext(),
                it
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        if (mainViewModel.isLogin()) {
            val username = mainViewModel.getCurrentUsername()
            if (username != null) {
                binding.layoutHeader.tvName.text = getString(R.string.text_name, username)
            } else {
                binding.layoutHeader.tvName.text = getString(R.string.default_username)
            }
        } else {
            binding.layoutHeader.tvName.text = getString(R.string.default_username)
        }
    }

    private fun getMenuData(categoryName: String? = null) {
        viewModel.getMenu(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                    binding.pbLoadingCategory.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_error_category),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingCategory.isVisible = true
                }
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
                    binding.pbLoadingMenu.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_error_menu),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingMenu.isVisible = true
                }
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