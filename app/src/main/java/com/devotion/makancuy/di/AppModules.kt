package com.devotion.makancuy.di

import android.content.SharedPreferences
import com.devotion.makancuy.data.datasource.auth.AuthDataSource
import com.devotion.makancuy.data.datasource.auth.FirebaseAuthDataSource
import com.devotion.makancuy.data.datasource.cart.CartDataSource
import com.devotion.makancuy.data.datasource.cart.CartDatabaseDataSource
import com.devotion.makancuy.data.datasource.category.CategoryApiDataSource
import com.devotion.makancuy.data.datasource.category.CategoryDataSource
import com.devotion.makancuy.data.datasource.menu.MenuApiDataSource
import com.devotion.makancuy.data.datasource.menu.MenuDataSource
import com.devotion.makancuy.data.datasource.user.UserDataSource
import com.devotion.makancuy.data.datasource.user.UserDataSourceImpl
import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSource
import com.devotion.makancuy.data.datasource.userpref.UserPreferenceDataSourceImpl
import com.devotion.makancuy.data.repository.CartRepository
import com.devotion.makancuy.data.repository.CartRepositoryImpl
import com.devotion.makancuy.data.repository.CategoryRepository
import com.devotion.makancuy.data.repository.CategoryRepositoryImpl
import com.devotion.makancuy.data.repository.MenuRepository
import com.devotion.makancuy.data.repository.MenuRepositoryImpl
import com.devotion.makancuy.data.repository.UserPreferenceRepository
import com.devotion.makancuy.data.repository.UserPreferenceRepositoryImpl
import com.devotion.makancuy.data.repository.UserRepository
import com.devotion.makancuy.data.repository.UserRepositoryImpl
import com.devotion.makancuy.data.source.local.database.AppDatabase
import com.devotion.makancuy.data.source.local.database.dao.CartDao
import com.devotion.makancuy.data.source.local.pref.UserPreference
import com.devotion.makancuy.data.source.local.pref.UserPreferenceImpl
import com.devotion.makancuy.data.source.network.service.RestaurantApiService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseService
import com.devotion.makancuy.data.source.network.service.firebase.FirebaseServiceImpl
import com.devotion.makancuy.presentation.cart.CartViewModel
import com.devotion.makancuy.presentation.checkout.CheckoutViewModel
import com.devotion.makancuy.presentation.detailmenu.DetailMenuViewModel
import com.devotion.makancuy.presentation.home.HomeViewModel
import com.devotion.makancuy.presentation.login.LoginViewModel
import com.devotion.makancuy.presentation.main.MainViewModel
import com.devotion.makancuy.presentation.profile.ProfileViewModel
import com.devotion.makancuy.presentation.register.RegisterViewModel
import com.devotion.makancuy.utils.SharedPreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<RestaurantApiService> { RestaurantApiService.invoke() }
        }

    private val firebaseModule =
        module {
            single<FirebaseAuth> {
                FirebaseAuth.getInstance()
            }
            single<FirebaseService> {
                FirebaseServiceImpl(get())
            }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val datasource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<MenuDataSource> { MenuApiDataSource(get()) }
            single<UserDataSource> { UserDataSourceImpl(get()) }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
            single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<MenuRepository> { MenuRepositoryImpl(get()) }
            single<UserPreferenceRepository> { UserPreferenceRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::MainViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModelOf(::DetailMenuViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::RegisterViewModel)
            viewModel { params ->
                DetailMenuViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
        }

    val modules =
        listOf<Module>(
            networkModule,
            firebaseModule,
            localModule,
            datasource,
            repository,
            viewModelModule,
        )
}
