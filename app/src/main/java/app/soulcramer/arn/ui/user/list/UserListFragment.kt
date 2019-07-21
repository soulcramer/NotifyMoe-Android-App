package app.soulcramer.arn.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import app.soulcramer.arn.NotifyMoeFragment
import app.soulcramer.arn.core.bind
import app.soulcramer.arn.core.distinctUntilChanged
import app.soulcramer.arn.core.map
import app.soulcramer.arn.core.switchMap
import app.soulcramer.arn.databinding.FragmentUserListBinding
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.ui.common.SpacingItemDecorator
import app.soulcramer.arn.ui.common.ViewState
import app.soulcramer.arn.ui.common.recyclerview.HideImeOnScrollListener
import app.soulcramer.arn.ui.user.list.UserListContext.Action.SearchUser
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UserListFragment : NotifyMoeFragment() {

    private val userListViewModel by sharedViewModel<UserListViewModel>()

    private val controller: UserListEpoxyController by lazy {
        UserListEpoxyController(UserListTextCreator(context!!))
    }

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

        initController()
        initRecyclerView()
        binding.usersSwipeRefresh.setOnRefreshListener {
            userListViewModel.handle(SearchUser(forceRefresh = true))
        }
        bindState()

        scheduleStartPostponedTransitions()
    }

    override fun onDestroyView() {
        // If the user spam exit/enter the fragment the detach callback from the RecyclerView is called after the new
        // attach which crash the app because epoxy see that the controller is leaked.
        binding.usersRv.adapter = null
        super.onDestroyView()
    }

    private fun initController() {
        controller.callbacks = object : UserListEpoxyController.Callbacks {
            override fun onUserClicked(item: User) {
                val direction = UserListFragmentDirections.navigateToUserProfile(item.id, item.nickname)
                findNavController().navigate(direction)
            }

            override fun onFilterChanged(filter: String) {
                userListViewModel.handle(SearchUser(filter))
            }
        }
    }

    private fun initRecyclerView() {
        binding.usersRv.apply {
            addItemDecoration(SpacingItemDecorator(paddingLeft))
            addOnScrollListener(HideImeOnScrollListener())
            setControllerAndBuildModels(controller)
        }
    }

    private fun bindState() {
        userListViewModel.state.map {
            it.filter
        }.switchMap {
            userListViewModel.state
        }.distinctUntilChanged()
            .bind(this) {
                controller.viewState = it
            }

        userListViewModel.state.map { it.users }.distinctUntilChanged().bind(this, ::onUserListChanged)
        userListViewModel.state.map { it.isRefreshing }.distinctUntilChanged().bind(this, ::onIsRefreshingChanged)
    }

    private fun onStatusChanged(status: ViewState) {
        Timber.d("$status")
    }

    private fun onIsRefreshingChanged(isRefreshing: Boolean) {
        binding.usersSwipeRefresh.isRefreshing = isRefreshing
    }

    private fun onUserListChanged(users: PagedList<User>?) {
        controller.submitList(users)
    }
}
