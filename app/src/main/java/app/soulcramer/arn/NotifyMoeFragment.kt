package app.soulcramer.arn

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater

/**
 * Base fragment class which supports LifecycleOwner and Dagger injection.
 */
abstract class NotifyMoeFragment : Fragment() {
    private var postponedTransition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransitionInflater.from(context).run {
            enterTransition = inflateTransition(R.transition.fragment_enter)
            exitTransition = inflateTransition(R.transition.fragment_exit)
        }
    }

    override fun postponeEnterTransition() {
        super.postponeEnterTransition()
        postponedTransition = true
    }

    override fun onStart() {
        super.onStart()

        if (postponedTransition) {
            // If we're postponedTransition and haven't started a transition yet, we'll delay for a max of 5 seconds
            view?.postDelayed(::scheduleStartPostponedTransitions, 5000)
        }
    }

    override fun startPostponedEnterTransition() {
        postponedTransition = false
        super.startPostponedEnterTransition()
    }

    protected fun scheduleStartPostponedTransitions() {
        if (postponedTransition) {
            requireView().doOnNextLayout {
                (it.parent as ViewGroup).doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
            postponedTransition = false
        }

        val activity = activity
        if (activity is NotifyMoeActivity) {
            activity.scheduleStartPostponedTransitions()
        }
    }
}