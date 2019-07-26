package app.soulcramer.arn.ui.common

import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import app.soulcramer.arn.R
import app.soulcramer.arn.domain.SortOption
import app.soulcramer.arn.ui.common.widget.PopupMenuButton

class SortPopupMenuListener(
    private val selectedSort: SortOption,
    private val availableSorts: List<SortOption>
) : PopupMenuButton.PopupMenuListener {
    override fun onPreparePopupMenu(popupMenu: PopupMenu) {
        val menu = popupMenu.menu
        for (index in 0 until menu.size()) {
            val menuItem = menu[index]
            val sortOption = popupMenuItemIdToSortOption(menuItem.itemId) ?: break

            menuItem.isVisible = availableSorts.contains(sortOption)
            if (selectedSort == sortOption) {
                menuItem.isChecked = true
            }
        }
    }
}

fun popupMenuItemIdToSortOption(itemId: Int) = when (itemId) {
    R.id.popup_sort_alpha -> SortOption.ALPHABETICAL
    else -> null
}