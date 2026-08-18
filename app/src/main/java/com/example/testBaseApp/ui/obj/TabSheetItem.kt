package com.example.testBaseApp.ui.obj

sealed class TabSheetItem {
    data class TabSheetItemContent(val name: String) : TabSheetItem()
    object AddSheet : TabSheetItem()
}