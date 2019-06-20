package app.soulcramer.arn.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.soulcramer.arn.NotifyMoeFragment
import app.soulcramer.arn.databinding.FragmentUserListBinding
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.SpacingItemDecorator
import app.soulcramer.arn.ui.common.ViewState
import app.soulcramer.arn.ui.common.recyclerview.HideImeOnScrollListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UserListFragment : NotifyMoeFragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private val controller: UserListEpoxyController by inject()

    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        controller.callbacks = object : UserListEpoxyController.Callbacks {
            override fun onUserClicked(item: User) {
                // Todo navigate to user
            }
        }

        binding.usersRv.apply {
            addItemDecoration(SpacingItemDecorator(paddingLeft))
            //            addOnScrollListener(StickyHeaderScrollListener(controller, controller::isHeader, binding.headerHolder))
            addOnScrollListener(HideImeOnScrollListener())
            setController(controller)
        }

        binding.usersSwipeRefresh.setOnRefreshListener { userListViewModel.handle(UserListContext.Action.Refresh) }

        //        sessionViewModel.state.map { it.loggedUserId }.distinctUntilChanged().bind(this, ::onLoggedUserChanged)
    }

    private fun onStatusChanged(status: ViewState) {
        Timber.d("$status")
        //        binding.status = when (status) {
        //            is Data -> "success"
        //            is Error -> "error"
        //            is Empty -> "empty"
        //            is Loading -> "loading"
        //        }
    }
}
