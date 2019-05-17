package app.soulcramer.arn.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.soulcramer.arn.GlideApp
import app.soulcramer.arn.databinding.FragmentAnimeDetailBinding
import app.soulcramer.arn.model.anime.Genre
import app.soulcramer.arn.ui.anime.MangaDetailsContext.Action.LoadMangaInformations
import app.soulcramer.arn.ui.common.statefulview.Data
import app.soulcramer.arn.ui.common.statefulview.Loading
import bind
import distinctUntilChanged
import map
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AnimeDetailFragment : Fragment() {
    private val args by navArgs<AnimeDetailFragmentArgs>()
    private val animeDetailViewModel by viewModel<AnimeDetailViewModel> { parametersOf(args.animeId) }
    private lateinit var binding: FragmentAnimeDetailBinding
    private val textCreator: AnimeDetailsTextCreator by currentScope.inject { parametersOf(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnimeDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.statefulView.state = Data()
        animeDetailViewModel.state.map { it.poster }.distinctUntilChanged().bind(this, this::onThumbnailChanged)
        animeDetailViewModel.state.map { it.title }.distinctUntilChanged().bind(this, this::onTitleChanged)
        animeDetailViewModel.state.map { it.summary }.distinctUntilChanged().bind(this, this::onSummaryChanged)
        animeDetailViewModel.state.map { it.genres }.distinctUntilChanged().bind(this, this::onGenresChanged)
        animeDetailViewModel.handle(LoadMangaInformations)
    }

    private fun onThumbnailChanged(newPoster: String) {
        GlideApp.with(this)
            .load(newPoster)
            .into(binding.poster)
    }

    private fun onTitleChanged(newTitle: CharSequence) {
        binding.title = newTitle
    }

    private fun onSummaryChanged(newSummary: CharSequence) {
        binding.summary = newSummary
    }

    private fun onViewStateChanged(isLoading: Boolean) {
        binding.statefulView.state = if (isLoading) {
            Loading()
        } else {
            Data()
        }
    }

    private fun onGenresChanged(newGenres: List<Genre>?) {
        binding.genresString = textCreator.genreString(newGenres)
        binding.genresContentDescription = textCreator.genreContentDescription(newGenres)
    }
}
