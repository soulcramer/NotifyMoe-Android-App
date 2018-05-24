package studio.lunabee.arn.ui.animelist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import studio.lunabee.arn.R
import studio.lunabee.arn.common.observeK
import studio.lunabee.arn.di.Injectable
import javax.inject.Inject

class AnimeListFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var itemAdapter: ItemAdapter<SimpleAnimeListItem>
    private lateinit var fastAdapter: FastAdapter<SimpleAnimeListItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animelist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(AnimeListViewModel::class.java)

        itemAdapter = ItemAdapter()
        fastAdapter = FastAdapter.with(itemAdapter)

        recyclerView.apply {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }


        animeListViewModel.setUserId("4J6qpK1ve")
        animeListViewModel.mItems.observeK(this) { userResource ->
            userResource?.data?.run {
                val items = this.asSequence().filter {
                    it.status == "watching"
                }.map {
                        SimpleAnimeListItem().apply {
                            title = it.animeId
                        }
                    }.toList()
                itemAdapter.add(items)
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
