package app.soulcramer.arn.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.soulcramer.arn.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : Fragment() {

    private val dashboardViewModel by sharedViewModel<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user, container, false)

}