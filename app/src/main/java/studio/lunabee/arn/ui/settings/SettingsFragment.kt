package studio.lunabee.arn.ui.settings

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user.*
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import studio.lunabee.arn.ui.user.UserViewModel
import javax.inject.Inject

class SettingsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserViewModel::class.java)


        userViewModel.setNickname(arguments?.getString(LOGIN_KEY))
        userViewModel.user.observeK(this) { userResource ->
            userResource?.data?.firstOrNull()?.run {
                nickNameTextView.text = nickName
                roleTextView.text = role
            }
        }
    }

    companion object {
        private const val LOGIN_KEY = "login"

        fun create(login: String): SettingsFragment {
            val userFragment = SettingsFragment()
            val bundle = Bundle()
            bundle.putString(LOGIN_KEY, login)
            userFragment.arguments = bundle
            return userFragment
        }
    }
}
