package studio.lunabee.arn.ui.user

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.*
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import studio.lunabee.arn.vo.Resource
import javax.inject.Inject

class UserFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(UserViewModel::class.java)

        userViewModel.setNickname("Scott")
        userViewModel.user.observeK(this) { userResource ->
            when (userResource) {
                is Resource.Success -> {
                    userResource.data?.run {
                        nickNameTextView.text = nickName
                        roleTextView.text = role
                        Picasso.get()
                            .load("https://media.notify.moe/images/avatars/large/$id.png")
                            .priority(Picasso.Priority.HIGH)
                            .into(avatarImageView)
                        Picasso.get()
                            .load("https://media.notify.moe/images/covers/large/$id.jpg")
                            .priority(Picasso.Priority.NORMAL)
                            .into(coverImageView)
                    }
                }
            }
        }
    }
}
