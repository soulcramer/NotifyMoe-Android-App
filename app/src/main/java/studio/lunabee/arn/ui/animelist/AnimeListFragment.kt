package studio.lunabee.arn.ui.animelist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.default_error_view.*
import kotlinx.android.synthetic.main.fragment_animelist.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import studio.lunabee.arn.ui.common.EqualSpacingItemDecoration
import studio.lunabee.arn.ui.common.dpToPx
import studio.lunabee.arn.ui.common.statefulview.Data
import studio.lunabee.arn.ui.user.UserViewModel
import studio.lunabee.arn.vo.Error
import studio.lunabee.arn.vo.Loading
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.Success
import studio.lunabee.arn.vo.animelist.AnimeListItem
import javax.inject.Inject

class AnimeListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var itemAdapter: ItemAdapter<SimpleAnimeListItem>
    private lateinit var fastAdapter: FastAdapter<SimpleAnimeListItem>

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animelist, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(AnimeListViewModel::class.java)
        userViewModel = ViewModelProviders.of(requireActivity(),
            viewModelFactory).get(UserViewModel::class.java)

        initViews()

        userViewModel.userId.observeK(this, animeListViewModel::setUserId)
        animeListViewModel.mItems.observeK(this, this::handleStatus)
    }

    private fun initViews() {
        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)

        recyclerView.apply {
            adapter = fastAdapter
            val columns = resources.getInteger(R.integer.animelist_columns)
            layoutManager = GridLayoutManager(context, columns)

            val itemsPadding = 20f.dpToPx(resources.displayMetrics)
            addItemDecoration(EqualSpacingItemDecoration(itemsPadding,
                EqualSpacingItemDecoration.GRID))
        }
    }

    private fun handleStatus(userResource: Resource<List<AnimeListItem>>) {
        when (userResource.status) {
            is Loading -> statefulView.state = statefulView.loadingState
            is Error -> {
                subtitle.text = userResource.message
                statefulView.state = statefulView.errorState
            }
            is Success -> {
                userResource.data?.let(this::handleData)
            }
        }
    }

    private fun handleData(animeListItems: List<AnimeListItem>) {
        job = launch(CommonPool) {
            val items = processData(animeListItems)
            showResult(items)
        }
    }

    private suspend fun showResult(items: List<SimpleAnimeListItem>) {
        withContext(UI) {
            if (items.isEmpty()) {
                statefulView.state = statefulView.emptyState
            } else {
                statefulView.state = Data()
                itemAdapter.setNewList(items)
            }
        }
    }

    private fun processData(
        animeListItems: List<AnimeListItem>): List<SimpleAnimeListItem> {
        return animeListItems.asSequence()
            .filter {
                it.status == "watching"
            }.map(this@AnimeListFragment::toFaItem)
            .toList()
    }

    override fun onDestroyView() {
        job?.cancel()
        super.onDestroyView()
    }

    private fun toFaItem(it: AnimeListItem): SimpleAnimeListItem {
        return SimpleAnimeListItem().apply {
            title = it.animeId
            withIdentifier(it.animeId.hashCode().toLong())
            withOnItemClickListener { v, _, item, _ ->
                val direction = AnimeListFragmentDirections.ItemClick(item.title)
                v?.findNavController()?.navigate(direction)
                true
            }
        }
    }
}
