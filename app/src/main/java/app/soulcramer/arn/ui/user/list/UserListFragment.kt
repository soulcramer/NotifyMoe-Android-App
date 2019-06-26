package app.soulcramer.arn.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.soulcramer.arn.NotifyMoeFragment
import app.soulcramer.arn.core.bind
import app.soulcramer.arn.core.distinctUntilChanged
import app.soulcramer.arn.core.map
import app.soulcramer.arn.databinding.FragmentUserListBinding
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.SpacingItemDecorator
import app.soulcramer.arn.ui.common.ViewState
import app.soulcramer.arn.ui.common.recyclerview.HideImeOnScrollListener
import app.soulcramer.arn.ui.user.list.UserListContext.Action.SearchUser
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class UserListFragment : NotifyMoeFragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private val controller: UserListEpoxyController by inject { parametersOf(context) }

    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        controller.callbacks = object : UserListEpoxyController.Callbacks {
            override fun onUserClicked(item: User) {
                val direction = UserListFragmentDirections.navigateToUserProfile(item.id, item.nickname)
                findNavController().navigate(direction)
            }

            override fun onFilterChanged(filter: String) {
                userListViewModel.handle(SearchUser(filter))
            }
        }

        binding.usersRv.apply {
            addItemDecoration(SpacingItemDecorator(paddingLeft))
            addOnScrollListener(HideImeOnScrollListener())
            setController(controller)
        }

        binding.usersSwipeRefresh.setOnRefreshListener { userListViewModel.handle(SearchUser(forceRefresh = true)) }

        userListViewModel.state.distinctUntilChanged().bind(this) { controller.viewState = it }
        userListViewModel.state.map { it.users }.distinctUntilChanged().bind(this, ::onUserListChanged)
        userListViewModel.state.map { it.isRefreshing }.distinctUntilChanged().bind(this, ::onIsRefreshingChanged)

        if (savedInstanceState == null) {
            userListViewModel.handle(SearchUser())
        }
        scheduleStartPostponedTransitions()
    }

    private fun onStatusChanged(status: ViewState) {
        Timber.d("$status")
    }

    private fun onIsRefreshingChanged(isRefreshing: Boolean) {
        binding.usersSwipeRefresh.isRefreshing = isRefreshing
    }

    private fun onUserListChanged(users: List<User>) {
        controller.setData(users)
    }
}
