package studio.lunabee.arn.ui.common.statefulview

import android.view.View

sealed class State(open var view: View? = null)
data class Loading(override var view: View? = null) : State(view)
class Empty(override var view: View? = null) : State(view)
class EmptyAfterSearch(override var view: View? = null) : State(view)
class Error(override var view: View? = null) : State(view)
class NoNetwork(override var view: View? = null) : State(view)
class Data : State()
