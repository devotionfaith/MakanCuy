package com.devotion.makancuy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.category.CategoryApiDataSource
import com.devotion.makancuy.data.datasource.menu.MenuApiDataSource
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.CategoryRepositoryImpl
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.MenuRepositoryImpl
import com.devotion.makancuy.data.source.local.pref.UserPreferenceImpl
import com.devotion.makancuy.data.source.network.service.RestaurantApiService
import com.devotion.makancuy.databinding.FragmentHomeBinding
import com.devotion.makancuy.presentation.detailmenu.DetailMenuActivity
import com.devotion.makancuy.presentation.home.adapter.CategoryAdapter
import com.devotion.makancuy.presentation.home.adapter.MenuAdapter
import com.devotion.makancuy.utils.GenericViewModelFactory
import com.devotion.makancuy.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = RestaurantApiService.invoke()
        val categoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val userPreference = UserPreferenceImpl(requireContext())
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                menuRepository,
                userPreference
            )
        )
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
        setupCategory()
        setupMenu()
        observeGridMode()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private fun getMenuData(categoryName: String? = null) {
        viewModel.getMenu(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
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

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setIcon(isUsingGridMode)
            changeLayout(isUsingGridMode)
        }
    }

    private fun changeLayout(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter.updateListMode(listMode)
        setupMenu()
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), if (usingGridMode) 2 else 1)
        }
    }

    private fun setClickAction() {
        binding.ivChangeLayout.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.ivChangeLayout.setImageResource(if (usingGridMode) R.drawable.ic_list else R.drawable.ic_grid)
    }
}