package studio.lunabee.arn.ui.anime

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.default_error_view.*
import kotlinx.android.synthetic.main.fragment_animelist.*
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import studio.lunabee.arn.vo.Error
import studio.lunabee.arn.vo.Loading
import studio.lunabee.arn.vo.Success
import javax.inject.Inject

class AnimeDetailFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var animeDetailViewModel: AnimeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animedetail, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeDetailViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(AnimeDetailViewModel::class.java)
        animeDetailViewModel.setAnimeId("4J6qpK1ve")
        animeDetailViewModel.anime.observeK(this) { userResource ->
            when (userResource.status) {
                is Loading -> statefulView.state = statefulView.loadingState
                is Error -> {
                    subtitle.text = userResource.message
                    statefulView.state = statefulView.errorState
                }
                is Success -> {
                }
            }
        }
    }
}
