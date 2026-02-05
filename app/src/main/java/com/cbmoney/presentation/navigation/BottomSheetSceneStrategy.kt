package com.cbmoney.presentation.navigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import com.cbmoney.presentation.navigation.BottomSheetSceneStrategy.Companion.bottomSheet
import com.cbmoney.presentation.theme.CBMoneyColors


/** An [OverlayScene] that renders an [entry] within a [ModalBottomSheet]. */
@OptIn(ExperimentalMaterial3Api::class)
internal class BottomSheetScene<T : Any>(
    override val key: T,
    override val previousEntries: List<NavEntry<T>>,
    override val overlaidEntries: List<NavEntry<T>>,
    private val entry: NavEntry<T>,
    private val modalBottomSheetProperties: ModalBottomSheetProperties,
    private val sheetState: SheetState?,
    private val onBack: () -> Unit,
) : OverlayScene<T> {

    override val entries: List<NavEntry<T>> = listOf(entry)

    override val content: @Composable (() -> Unit) = {
        ModalBottomSheet(
            sheetState = sheetState ?: rememberModalBottomSheetState(),
            onDismissRequest = onBack,
            properties = modalBottomSheetProperties,
            containerColor = CBMoneyColors.BackGround.BackgroundPrimary,
            dragHandle = null
        ) {
            entry.Content()
        }
    }
}

/**
 * A [SceneStrategy] that displays entries that have added [bottomSheet] to their [NavEntry.metadata]
 * within a [ModalBottomSheet] instance.
 *
 * This strategy should always be added before any non-overlay scene strategies.
 */
@OptIn(ExperimentalMaterial3Api::class)
class BottomSheetSceneStrategy<T : Any> : SceneStrategy<T> {
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        val lastEntry = entries.lastOrNull()
        val metadata = lastEntry?.metadata ?: return null
        val data = metadata[BOTTOM_SHEET_KEY] as? Map<*, *>
        val sheetState = data?.get("sheetState") as? SheetState
        val bottomSheetProperties = data?.get("properties") as? ModalBottomSheetProperties


        return bottomSheetProperties?.let { properties ->
            @Suppress("UNCHECKED_CAST")
            BottomSheetScene(
                key = lastEntry.contentKey as T,
                previousEntries = entries.dropLast(1),
                overlaidEntries = entries.dropLast(1),
                entry = lastEntry,
                sheetState = sheetState,
                modalBottomSheetProperties = properties,
                onBack = onBack
            )
        }
    }

    companion object {
        /**
         * Function to be called on the [NavEntry.metadata] to mark this entry as something that
         * should be displayed within a [ModalBottomSheet].
         *
         * @param modalBottomSheetProperties properties that should be passed to the containing
         * [ModalBottomSheet].
         */
        @Composable
        @OptIn(ExperimentalMaterial3Api::class)
        fun bottomSheet(
            sheetState: SheetState = rememberModalBottomSheetState(),
            modalBottomSheetProperties: ModalBottomSheetProperties = ModalBottomSheetProperties()
        ): Map<String, Map<String, Any>> =
                mapOf(BOTTOM_SHEET_KEY to mapOf(
                    "sheetState" to sheetState,
                    "properties" to modalBottomSheetProperties
                )
            )

        internal const val BOTTOM_SHEET_KEY = "bottomsheet"
    }
}