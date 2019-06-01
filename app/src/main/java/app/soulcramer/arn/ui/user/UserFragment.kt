package app.soulcramer.arn.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.soulcramer.arn.core.bind
import app.soulcramer.arn.core.distinctUntilChanged
import app.soulcramer.arn.core.map
import app.soulcramer.arn.databinding.FragmentUserBinding
import app.soulcramer.arn.domain.interactor.Status
import app.soulcramer.arn.ui.user.UserContext.Action.LoadUser
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserFragment : Fragment() {

    private val userViewModel by sharedViewModel<UserViewModel>()

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        userViewModel.handle(LoadUser("VJOK1ckvx"))

    }

    private fun onStatusChanged(status: Status) {
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
