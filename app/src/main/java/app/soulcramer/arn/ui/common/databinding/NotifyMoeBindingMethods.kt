package app.soulcramer.arn.ui.common.databinding

import android.view.View
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingMethods(
    BindingMethod(
        type = View::class,
        attribute = "outlineProviderInstance",
        method = "setOutlineProvider"
    ),
    BindingMethod(
        type = SwipeRefreshLayout::class,
        attribute = "isRefreshing",
        method = "setRefreshing"
    ),
    BindingMethod(
        type = View::class,
        attribute = "clipToOutline",
        method = "setClipToOutline"
    ),
    BindingMethod(
        type = View::class,
        attribute = "activated",
        method = "setActivated"
    )
)
class NotifyMoeBindingMethods