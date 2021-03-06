package app.soulcramer.arn.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.soulcramer.arn.core.bind
import app.soulcramer.arn.core.distinctUntilChanged
import app.soulcramer.arn.core.map
import app.soulcramer.arn.databinding.FragmentUserBinding
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Empty
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import app.soulcramer.arn.ui.common.NetworkError
import app.soulcramer.arn.ui.common.ViewState
import app.soulcramer.arn.ui.user.UserContext.Action.LoadUser
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UserFragment : Fragment() {

    val userViewModel by sharedViewModel<UserViewModel>()
    private val args by navArgs<UserFragmentArgs>()

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.state.map { it.avatar }.distinctUntilChanged().bind(this, ::onAvatarChanged)
        userViewModel.state.map { it.cover }.distinctUntilChanged().bind(this, ::onCoverChanged)
        userViewModel.state.map { it.name }.distinctUntilChanged().bind(this, ::onNameChanged)
        userViewModel.state.map { it.title }.distinctUntilChanged().bind(this, ::onRoleChanged)
        userViewModel.state.map { it.status }.distinctUntilChanged().bind(this, ::onStatusChanged)

        userViewModel.handle(LoadUser(args.userId))
    }

    private fun onStatusChanged(status: ViewState) {
        Timber.d("$status")
        binding.status = when (status) {
            is Data -> "success"
            is Error -> "error"
            is Empty -> "empty"
            is Loading -> "loading"
            NetworkError -> "network error"
        }
    }

    private fun onRoleChanged(role: String) {
        binding.role = role
    }

    private fun onNameChanged(name: String) {
        binding.nickname = name
    }

    private fun onCoverChanged(coverUrl: String) {
        binding.cover = coverUrl
    }

    private fun onAvatarChanged(avatarUrl: String) {
        binding.avatar = avatarUrl
    }
}
