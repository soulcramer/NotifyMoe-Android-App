package studio.lunabee.arn.ui.dashboard

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
import javax.inject.Inject

class DashboardFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DashboardViewModel::class.java)


        dashboardViewModel.setNickname("Scott")
        dashboardViewModel.user.observeK(this) { userResource ->
            userResource?.data?.firstOrNull()?.run {
                nickNameTextView.text = nickName
                roleTextView.text = role
                Picasso.get().load("https://media.notify.moe/images/avatars/large/$id.png").into(
                    avatarImageView)
                Picasso.get().load("https://media.notify.moe/images/covers/large/$id.jpg").into(
                    coverImageView)
            }
        }
    }
}
