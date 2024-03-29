package com.devotion.makancuy.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devotion.makancuy.R
import com.devotion.makancuy.data.datasource.category.CategoryDataSource
import com.devotion.makancuy.data.datasource.category.DummyCategoryDataSource
import com.devotion.makancuy.data.datasource.menu.DummyMenuDataSource
import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.CategoryRepositoryImpl
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.MenuRepositoryImpl
import com.devotion.makancuy.databinding.FragmentCartBinding
import com.devotion.makancuy.databinding.FragmentHomeBinding
import com.devotion.makancuy.presentation.detailmenu.DetailMenuActivity
import com.devotion.makancuy.presentation.home.adapter.CategoryAdapter
import com.devotion.makancuy.presentation.home.adapter.MenuAdapter
import com.devotion.makancuy.presentation.home.adapter.OnItemCLickedListener
import com.devotion.makancuy.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var menuAdapter: MenuAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private val viewModel: HomeViewModel by viewModels {
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository,menuRepository))
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
        bindCategory(viewModel.getCategory())
        observeGridMode()
        setClickAction()
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner){ isUsingGridMode->
            setIcon(isUsingGridMode)
            bindMenuList(isUsingGridMode, viewModel.getMenu())
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

    private fun bindCategory(data: List<Category>) {
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        categoryAdapter?.submitDataCategory(data)
    }

    private fun bindMenuList(isUsingGrid: Boolean, data: List<Menu>) {
        val listMode = if (isUsingGrid) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter = MenuAdapter(
            listMode = listMode,
            listener = object : OnItemCLickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvMenu.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        menuAdapter?.submitData(data)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(
            requireContext(),
            item
        )
    }
}