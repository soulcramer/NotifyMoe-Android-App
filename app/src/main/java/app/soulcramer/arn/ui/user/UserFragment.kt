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

    }

    private fun onStatusChanged(status: Status) {
        TODO("not implemented")
    }

    private fun onRoleChanged(role: String) {
        TODO("not implemented")
    }

    private fun onNameChanged(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onCoverChanged(coverUrl: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onAvatarChanged(avatarUrl: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
