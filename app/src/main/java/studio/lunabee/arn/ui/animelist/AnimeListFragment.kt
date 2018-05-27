package studio.lunabee.arn.ui.animelist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.default_error_view.*
import kotlinx.android.synthetic.main.fragment_animelist.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import studio.lunabee.arn.ui.common.EqualSpacingItemDecoration
import studio.lunabee.arn.ui.common.dpToPx
import studio.lunabee.arn.ui.common.statefulview.Data
import studio.lunabee.arn.vo.Error
import studio.lunabee.arn.vo.Loading
import studio.lunabee.arn.vo.Success
import javax.inject.Inject

class AnimeListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var itemAdapter: ItemAdapter<SimpleAnimeListItem>
    private lateinit var fastAdapter: FastAdapter<SimpleAnimeListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animelist, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(AnimeListViewModel::class.java)

        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)

        recyclerView.apply {
            adapter = fastAdapter
            val columns = resources.getInteger(R.integer.animelist_columns)
            layoutManager = GridLayoutManager(context, columns)

            val itemsPadding = 20f.dpToPx(resources.displayMetrics)
            addItemDecoration(EqualSpacingItemDecoration(itemsPadding,
                EqualSpacingItemDecoration.GRID))

            setHasFixedSize(true)
        }

        animeListViewModel.setUserId("4J6qpK1ve")
        animeListViewModel.mItems.observeK(this) { userResource ->
            when (userResource.status) {
                is Loading -> statefulView.state = statefulView.loadingState
                is Error -> {
                    subtitle.text = userResource.message
                    statefulView.state = statefulView.errorState
                }
                is Success -> {
                    userResource.data?.let { animeListItems ->
                        launch(CommonPool) {
                            val items = animeListItems.asSequence().filter {
                                it.status == "watching"
                            }.map {
                                SimpleAnimeListItem().apply {
                                    title = it.animeId
                                    withIdentifier(it.animeId.hashCode().toLong())
                                }
                            }.toList()

                            launch(UI) {
                                statefulView.state = if (items.isEmpty()) {
                                    statefulView.emptyState
                                } else {
                                    itemAdapter.setNewList(items)
                                    Data()
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    companion object {
        private const val LOGIN_KEY = "login"

        fun create(login: String): AnimeListFragment {
            val userFragment = AnimeListFragment()
            val bundle = Bundle()
            bundle.putString(LOGIN_KEY, login)
            userFragment.arguments = bundle
            return userFragment
        }
    }
}
