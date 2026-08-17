package com.example.testBaseApp.ui.main

import androidx.lifecycle.ViewModel
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.ui.editor.ExcelSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class EditType{
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
}

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    var listColorBackGround = emptyList<String>()

    var toggleEditing : MutableMap<EditType, Boolean> = mutableMapOf()

    init {
        setupColorBackgroundForEdit()
    }

    private fun setupColorBackgroundForEdit() {
        val colors = listOf(
            "#FF6B6B", // Coral
            "#4D96FF", // Blue
            "#6BCB77", // Green
            "#FFD93D", // Yellow
            "#845EC2", // Purple
            "#FF9671", // Orange
            "#00C9A7", // Teal
            "#C34A36", // Red Brown
            "#0081CF", // Deep Blue
            "#F9F871"  // Soft Yellow
        )
        listColorBackGround = colors
    }

    var document: SODoc? = null

    var selectionState: ExcelSelectionState? = null

    var currentSheetIndex: Int = 0

    fun toggleModeEditType(editType: EditType){
        toggleEditing[editType]?.let { toggleEditing[editType] = !it }
    }

}