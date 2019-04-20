package app.soulcramer.arn.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.soulcramer.arn.GlideApp
import app.soulcramer.arn.R
import app.soulcramer.arn.common.observeK
import com.bumptech.glide.Priority
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import javax.inject.Inject

class UserFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userViewModel by sharedViewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.setNickname("Scott")
        userViewModel.user.observeK(this) { userResource ->
            userResource.data?.run {
                nickNameTextView.text = nickName
                roleTextView.text = role
                GlideApp.with(this@UserFragment)
                    .saturateOnLoad()
                    .load("https://media.notify.moe/images/avatars/large/$id.png")
                    .priority(Priority.HIGH)
                    .into(avatarImageView)
                GlideApp.with(this@UserFragment)
                    .saturateOnLoad()
                    .load("https://media.notify.moe/images/covers/large/$id.jpg")
                    .priority(Priority.NORMAL)
                    .into(coverImageView)
            }
        }
    }
}
