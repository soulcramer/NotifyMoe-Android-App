package app.soulcramer.arn.ui.animelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import app.soulcramer.arn.R
import app.soulcramer.arn.common.observeK
import app.soulcramer.arn.ui.common.EqualSpacingItemDecoration
import app.soulcramer.arn.ui.common.dpToPx
import app.soulcramer.arn.ui.common.statefulview.Data
import app.soulcramer.arn.ui.user.UserViewModel
import app.soulcramer.arn.vo.Error
import app.soulcramer.arn.vo.Loading
import app.soulcramer.arn.vo.Resource
import app.soulcramer.arn.vo.Success
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.default_error_view.*
import kotlinx.android.synthetic.main.fragment_animelist.*
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeListFragment : Fragment() {
    private val animeListViewModel by viewModel<AnimeListViewModel>()
    private val userViewModel by sharedViewModel<UserViewModel>()

    private lateinit var itemAdapter: ItemAdapter<SimpleAnimeListItem>
    private lateinit var fastAdapter: FastAdapter<SimpleAnimeListItem>

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animelist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        userViewModel.userId.observeK(this, animeListViewModel::setUserId)
        animeListViewModel.items.observeK(this, this::handleStatus)
    }

    private fun initViews() {
        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)

        recyclerView.apply {
            adapter = fastAdapter
            val columns = resources.getInteger(R.integer.animelist_columns)
            layoutManager = GridLayoutManager(context, columns)

            val itemsPadding = 4f.dpToPx(resources.displayMetrics)
            addItemDecoration(EqualSpacingItemDecoration(itemsPadding,
                EqualSpacingItemDecoration.GRID))
        }
    }

    private fun handleStatus(userResource: Resource<List<SimpleAnimeListItem>>) {
        userResource.data?.run(::showResult)
            ?: let {
                when (userResource.status) {
                    is Loading -> statefulView.state = statefulView.loadingState
                    is Error -> {
                        subtitle.text = userResource.message
                        statefulView.state = statefulView.errorState
                    }
                    is Success -> statefulView.state = Data()
                }
            }
    }

    private fun showResult(items: List<SimpleAnimeListItem>) {
        if (items.isEmpty()) {
            statefulView.state = statefulView.emptyState
        } else {
            statefulView.state = Data()
            itemAdapter.setNewList(items)
        }
    }

    override fun onDestroyView() {
        job?.cancel()
        super.onDestroyView()
    }
}
