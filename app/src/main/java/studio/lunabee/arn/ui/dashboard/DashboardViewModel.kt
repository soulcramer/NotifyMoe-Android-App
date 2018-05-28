package studio.lunabee.arn.ui.dashboard

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import studio.lunabee.arn.repository.UserRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    private val _nick = MutableLiveData<String>()
    val nick: LiveData<String>
        get() = _nick

    fun setNickname(nick: String?) {
        if (_nick.value != nick) {
            _nick.value = nick
        }
    }

    fun retry() {
        _nick.value?.let {
            _nick.value = it
        }
    }
}
