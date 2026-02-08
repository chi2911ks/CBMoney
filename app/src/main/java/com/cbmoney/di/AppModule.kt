package com.cbmoney.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.cbmoney.data.local.datasource.BudgetLocalDataSource
import com.cbmoney.data.local.datasource.BudgetLocalDataSourceImpl
import com.cbmoney.data.local.datasource.CategoryLocalDataSource
import com.cbmoney.data.local.datasource.CategoryLocalDataSourceImpl
import com.cbmoney.data.local.datasource.TransactionLocalDataSource
import com.cbmoney.data.local.datasource.TransactionLocalDataSourceImpl
import com.cbmoney.data.local.datastore.DataStoreManager
import com.cbmoney.data.local.datastore.DataStoreManagerImpl
import com.cbmoney.data.local.room.CBMoneyDB
import com.cbmoney.data.provider.EmailAuthClient
import com.cbmoney.data.provider.GoogleAuthClient
import com.cbmoney.data.remote.datasource.CategoryRemoteDataSource
import com.cbmoney.data.remote.datasource.CategoryRemoteDataSourceImpl
import com.cbmoney.data.remote.datasource.UserRemoteDataSource
import com.cbmoney.data.remote.datasource.UserRemoteDataSourceImpl
import com.cbmoney.data.repository.BudgetRepositoryImpl
import com.cbmoney.data.repository.CategoryRepositoryImpl
import com.cbmoney.data.repository.UserRepositoryImpl
import com.cbmoney.domain.repository.BudgetRepository
import com.cbmoney.domain.repository.CategoryRepository
import com.cbmoney.domain.repository.TransactionRepository
import com.cbmoney.domain.repository.UserRepository
import com.cbmoney.domain.usecase.budget.GetBudgetsCategoryUseCase
import com.cbmoney.domain.usecase.budget.GetBudgetsSettingUseCase
import com.cbmoney.domain.usecase.budget.SaveBudgetsUseCase
import com.cbmoney.domain.usecase.category.DeleteCategoryUseCase
import com.cbmoney.domain.usecase.category.GetAllCategoriesUseCase
import com.cbmoney.domain.usecase.category.InitCategoriesDefaultUseCase
import com.cbmoney.domain.usecase.category.SaveCategoryUseCase
import com.cbmoney.domain.usecase.transaction.GetRecentTransactionsUseCase
import com.cbmoney.domain.usecase.transaction.SaveTransactionUseCase
import com.cbmoney.domain.usecase.user.GetUserUseCase
import com.cbmoney.domain.usecase.user.SaveUserToUseCase
import com.cbmoney.presentation.app.AppSnackbarManager
import com.cbmoney.presentation.buget.viewmodel.BudgetSettingsViewModel
import com.cbmoney.presentation.buget.viewmodel.BudgetViewModel
import com.cbmoney.presentation.category.viewmodel.AddCategoryViewModel
import com.cbmoney.presentation.category.viewmodel.CategoriesViewModel
import com.cbmoney.presentation.category.viewmodel.EditCategoryViewModel
import com.cbmoney.presentation.home.HomeViewModel
import com.cbmoney.presentation.login.LoginViewModel
import com.cbmoney.presentation.main.MainViewModel
import com.cbmoney.presentation.profile.ProfileViewModel
import com.cbmoney.presentation.register.RegisterViewModel
import com.cbmoney.presentation.reports.ReportViewModel
import com.cbmoney.presentation.splash.SplashViewModel
import com.cbmoney.presentation.transaction.TransactionsViewModel
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
    single { EmailAuthClient(get()) }
    single { GoogleAuthClient(get()) }

    //AppSnackbarManager
    single { AppSnackbarManager() }
}
val useCaseModule = module {
    //user
    factoryOf(::GetUserUseCase)
    factoryOf(::SaveUserToUseCase)

    //category
    factoryOf(::InitCategoriesDefaultUseCase)
    factoryOf(::GetAllCategoriesUseCase)
    factoryOf(::DeleteCategoryUseCase)
    factoryOf(::SaveCategoryUseCase)



    //budget
    factoryOf(::SaveBudgetsUseCase)
    factoryOf(::GetBudgetsCategoryUseCase)
    factoryOf(::GetBudgetsSettingUseCase)

    //transaction
    factoryOf(::SaveTransactionUseCase)
    factoryOf(::GetRecentTransactionsUseCase)

}
val dataSourceModule = module {
    //Remote
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
    single<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(get()) }

    //Local
    single<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(get()) }
    single<BudgetLocalDataSource> { BudgetLocalDataSourceImpl(get()) }
    single<TransactionLocalDataSource> { TransactionLocalDataSourceImpl(get()) }
}
val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    single<BudgetRepository> { BudgetRepositoryImpl(get(), get(), get()) }
    single<TransactionRepository> { com.cbmoney.data.repository.TransactionRepositoryImpl(get(), get()) }
}
@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::TransactionsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::AddCategoryViewModel)
    viewModelOf(::EditCategoryViewModel)
    viewModelOf(::BudgetSettingsViewModel)
    viewModelOf(::BudgetViewModel)
    viewModelOf(::ReportViewModel)

}
val roomModule = module {
    single {
        CBMoneyDB.getInstance(get())
    }
    single { get<CBMoneyDB>().categoryDao() }
    single { get<CBMoneyDB>().budgetDao() }
    single { get<CBMoneyDB>().transactionDao() }
}