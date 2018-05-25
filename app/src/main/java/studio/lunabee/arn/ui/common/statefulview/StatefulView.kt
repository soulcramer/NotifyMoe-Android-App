package studio.lunabee.arn.ui.common.statefulview

import android.content.Context
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import studio.lunabee.arn.R
import java.util.ArrayList

class StatefulView : FrameLayout {

    private lateinit var container: FrameLayout
    private lateinit var progressBar: ContentLoadingProgressBar
    private val childViews = ArrayList<View>()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
        private set
    var errorState: Error = Error()
    var emptyState: Empty = Empty()
    var noNetworkState: NoNetwork = NoNetwork()
    var emptyAfterSearchState: EmptyAfterSearch = EmptyAfterSearch()
    var loadingState: Loading = Loading()
    var isSwipeRefreshEnable: Boolean = false
    var state: State = Data()
        set(state) {
            if (this.state != state) {
                field = state
                updateState()
            }
        }

    @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        isSwipeRefreshEnable = false
    }

    @Suppress("unused") @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context,
        attrs,
        defStyleAttr,
        defStyleRes) {
        isSwipeRefreshEnable = false
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.stateful_view, this)
        container = findViewById(R.id.lb_sfv_container)
        progressBar = findViewById(R.id.statefulViewProgessBar)
        progressBar.tag = TAG
        addErrorView(R.layout.default_error_view)
        addEmptyView(R.layout.default_empty_view)
        addEmptyAfterSearchView(R.layout.default_empty_view)
        addNoNetworkView(R.layout.default_no_network_view)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)
        swipeRefreshLayout.tag = TAG
        childViews.forEach(container::addView)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child.visibility == View.VISIBLE) {
            if (child.tag == null || child.tag != TAG) {
                if (child !is SwipeRefreshLayout) {
                    childViews.add(child)
                    return
                }
            }
        }
        super.addView(child, index, params)
    }

    private fun updateState() {
        when (state) {
        //FIXME: The loading states doesn't show the progress bar even when the show time is
        // higher than 500ms.
            is Loading -> {
                resetSwipeRefresh()
                emptyState.view?.visibility = GONE
                emptyAfterSearchState.view?.visibility = GONE
                errorState.view?.visibility = GONE
                noNetworkState.view?.visibility = GONE
                progressBar.show()
                setChildVisibility(View.GONE)
            }
            is Empty -> {
                resetSwipeRefresh()
                progressBar.hide()
                emptyState.view?.visibility = VISIBLE
                emptyAfterSearchState.view?.visibility = GONE
                errorState.view?.visibility = GONE
                noNetworkState.view?.visibility = GONE
                setChildVisibility(View.GONE)
            }
            is EmptyAfterSearch -> {
                resetSwipeRefresh()
                progressBar.hide()
                emptyState.view?.visibility = GONE
                emptyAfterSearchState.view?.visibility = VISIBLE
                errorState.view?.visibility = GONE
                noNetworkState.view?.visibility = GONE
                setChildVisibility(View.GONE)
            }
            is NoNetwork -> {
                resetSwipeRefresh()
                progressBar.hide()
                emptyState.view?.visibility = GONE
                emptyAfterSearchState.view?.visibility = GONE
                errorState.view?.visibility = GONE
                noNetworkState.view?.visibility = VISIBLE
                setChildVisibility(View.GONE)
            }
            is Error -> {
                resetSwipeRefresh()
                progressBar.hide()
                emptyState.view?.visibility = GONE
                emptyAfterSearchState.view?.visibility = GONE
                errorState.view?.visibility = VISIBLE
                noNetworkState.view?.visibility = GONE
                setChildVisibility(View.GONE)
            }
            is Data -> {
                resetSwipeRefresh()
                progressBar.hide()
                emptyState.view?.visibility = GONE
                emptyAfterSearchState.view?.visibility = GONE
                errorState.view?.visibility = GONE
                noNetworkState.view?.visibility = GONE
                setChildVisibility(View.VISIBLE)
            }
        }
    }

    fun resetSwipeRefresh() {
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isEnabled = isSwipeRefreshEnable
    }

    private fun setChildVisibility(visibility: Int) {
        for (view in childViews) {
            view.visibility = visibility
        }
    }

    fun addEmptyView(@LayoutRes emptyLayoutRes: Int) {
        val view = LayoutInflater.from(context).inflate(emptyLayoutRes, null)
        // Don't forget to remove the last state view!
        container.removeView(emptyState.view)
        emptyState = Empty(view)
        emptyState.view?.visibility = GONE
        emptyState.view?.tag = TAG
        container.addView(emptyState.view)
    }

    fun addErrorView(@LayoutRes errorLayoutRes: Int) {
        val view = LayoutInflater.from(context).inflate(errorLayoutRes, null)
        // Don't forget to remove the last state view!
        container.removeView(errorState.view)
        errorState = Error(view)
        errorState.view?.visibility = GONE
        errorState.view?.tag = TAG
        container.addView(errorState.view)
    }

    fun addEmptyAfterSearchView(@LayoutRes emptyAfterSearchLayoutRes: Int) {
        val view = LayoutInflater.from(context).inflate(emptyAfterSearchLayoutRes, null)
        // Don't forget to remove the last state view!
        container.removeView(emptyAfterSearchState.view)
        emptyAfterSearchState = EmptyAfterSearch(view)
        emptyAfterSearchState.view?.visibility = GONE
        emptyAfterSearchState.view?.tag = TAG
        container.addView(emptyAfterSearchState.view)
    }

    fun addNoNetworkView(@LayoutRes emptyAfterSearchLayoutRes: Int) {
        val view = LayoutInflater.from(context).inflate(emptyAfterSearchLayoutRes, null)
        // Don't forget to remove the last state view!
        container.removeView(noNetworkState.view)
        noNetworkState = NoNetwork(view)
        noNetworkState.view?.visibility = GONE
        noNetworkState.view?.tag = TAG
        container.addView(noNetworkState.view)
    }

    companion object {
        private const val TAG = "StatefulView"
    }
}
