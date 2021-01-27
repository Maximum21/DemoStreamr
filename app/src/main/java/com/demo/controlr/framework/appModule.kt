package com.demo.controlr.framework

import com.demo.controlr.data.repository.GeneralRepository
import com.demo.controlr.data.usercase.FetchPolicyTerm
import com.demo.controlr.framework.database.PreferenceManager
import com.demo.controlr.framework.network.ConnectActivity
import com.demo.controlr.framework.network.retrofit.ProviderRetrofit
import com.demo.controlr.presentation.connect.ConnectViewModel
import com.demo.controlr.presentation.dialog.main.SplashViewModel
import com.demo.controlr.presentation.exchnage.ExchangeViewModel
import com.demo.controlr.presentation.home.HomeViewModel
import com.demo.controlr.presentation.login.LoginViewModel
import com.demo.controlr.presentation.menu.MenuViewModel
import com.demo.controlr.presentation.moreoptions.MoreOptionsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        ConnectActivity(context = androidContext())
    }


    single {
        PreferenceManager(context = androidContext())
    }


    single {
        ProviderRetrofit(preferenceManager = get(),connectActivity = get())
    }

    single {
        createNetworkService<GeneralService>(providerRetrofit = get())
    }



    single {
        GeneralRepository(generalDataSource = GeneralImpl(preferenceManager = get(),generalService = get()))
    }

    single {
        Interactors(

            /**
             * MARK: general
             * */
            fetchPolicyTerm = FetchPolicyTerm(generalRepository = get())
        )
    }



    viewModel {
        SplashViewModel(interactors = get())
    }
    viewModel {
        HomeViewModel(interactors = get())
    }
    viewModel {
        ConnectViewModel(interactors = get())
    }
    viewModel {
        MoreOptionsViewModel(interactors = get())
    }
    viewModel {
        LoginViewModel(interactors = get())
    }
    viewModel {
        ExchangeViewModel(interactors = get())
    }
    viewModel {
        MenuViewModel(interactors = get())
    }

}

inline fun <reified T> createNetworkService(providerRetrofit: ProviderRetrofit) = providerRetrofit.createNetworkService<T>()