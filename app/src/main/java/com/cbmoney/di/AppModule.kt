package com.cbmoney.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.data.local.datastore.DataStoreManagerImpl
import com.cbmoney.data.provider.EmailAuth
import com.cbmoney.data.provider.GoogleAuth
import com.cbmoney.data.remote.datasource.CategoryRemoteDataSource
import com.cbmoney.data.remote.datasource.CategoryRemoteDataSourceImpl
import com.cbmoney.data.remote.datasource.UserRemoteDataSource
import com.cbmoney.data.remote.datasource.UserRemoteDataSourceImpl
import com.cbmoney.data.repository.UserRepositoryImpl
import com.cbmoney.domain.repository.UserRepository
import com.cbmoney.domain.usecase.user.GetUserUseCase
import com.cbmoney.domain.usecase.user.SaveUserToUseCase
import com.cbmoney.presentation.home.HomeViewModel
import com.cbmoney.presentation.login.LoginViewModel
import com.cbmoney.presentation.main.MainViewModel
import com.cbmoney.presentation.profile.ProfileViewModel
import com.cbmoney.presentation.register.RegisterViewModel
import com.cbmoney.presentation.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // DataStore
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile("cbmoney_preferences") }
        )
    }
    single<DataStoreManager> {
        DataStoreManagerImpl(get())
    }

    //firebase
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { EmailAuth(get()) }
    single { GoogleAuth(get()) }
}
val useCaseModule = module {
    factoryOf(::GetUserUseCase)
    factoryOf(::SaveUserToUseCase)
}
val dataSourceModule = module {
    //Remote
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }

}
val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

}
val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
}
