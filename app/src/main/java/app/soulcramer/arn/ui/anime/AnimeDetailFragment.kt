package app.soulcramer.arn.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import app.soulcramer.arn.R
import app.soulcramer.arn.common.observeK
import app.soulcramer.arn.ui.common.statefulview.Data
import app.soulcramer.arn.vo.Error
import app.soulcramer.arn.vo.Loading
import app.soulcramer.arn.vo.Success
import kotlinx.android.synthetic.main.default_error_view.*
import kotlinx.android.synthetic.main.fragment_animedetail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class AnimeDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val animeDetailViewModel by viewModel<AnimeDetailViewModel>()
    private val args by navArgs<AnimeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animedetail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animeDetailViewModel.setAnimeId(args.userId)
        animeDetailViewModel.anime.observeK(this) { userResource ->
            if (userResource.data != null) {
                userResource.data.let {
                    statefulView.state = Data()
                    animeTitleTextView.text = it.title?.canonical
                }

            } else {
                when (userResource.status) {
                    is Loading -> statefulView.state = statefulView.loadingState
                    is Error -> {
                        subtitle.text = userResource.message
                        statefulView.state = statefulView.errorState
                    }
                    is Success -> {
                        userResource.data?.let {
                            statefulView.state = Data()
                            animeTitleTextView.text = it.title?.canonical
                        }
                    }
                }
            }
        }
    }
}
