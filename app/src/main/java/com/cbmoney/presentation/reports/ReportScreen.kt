package com.cbmoney.presentation.reports

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cbmoney.presentation.reports.components.CategoryProcessBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val listData = uiState.categories.map { it.amount to Color(it.iconColor.toColorInt()) }

    Box(modifier = Modifier.fillMaxSize()){
        CategoryProcessBar(
            modifier = Modifier.align(Alignment.Center),
            listData = listData
        )
    }
}