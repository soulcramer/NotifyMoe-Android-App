package app.soulcramer.arn.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel(userRepository: UserRepository) : ViewModel() {
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
