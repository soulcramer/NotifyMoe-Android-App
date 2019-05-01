package app.soulcramer.arn.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.soulcramer.arn.common.observeK
import app.soulcramer.arn.databinding.FragmentAnimeDetailBinding
import app.soulcramer.arn.ui.common.statefulview.Data
import app.soulcramer.arn.vo.Error
import app.soulcramer.arn.vo.Loading
import app.soulcramer.arn.vo.Success
import kotlinx.android.synthetic.main.default_empty_view.view.*
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeDetailFragment : Fragment() {
    private val animeDetailViewModel by viewModel<AnimeDetailViewModel>()
    private val args by navArgs<AnimeDetailFragmentArgs>()
    private lateinit var binding: FragmentAnimeDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnimeDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animeDetailViewModel.setAnimeId(args.userId)
        animeDetailViewModel.anime.observeK(this) { userResource ->
            if (userResource.data != null) {
                userResource.data.let {
                    binding.statefulView.state = Data()
                    binding.anime = it
                }

            } else {
                when (userResource.status) {
                    is Loading -> statefulView.state = statefulView.loadingState
                    is Error -> {
                        binding.statefulView.subtitle.text = userResource.message
                        binding.statefulView.state = statefulView.errorState
                    }
                    is Success -> {
                        userResource.data?.let {
                            binding.statefulView.state = Data()
                            binding.anime = it
                        }
                    }
                }
            }
        }
    }
}
