package studio.lunabee.arn.ui.user

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import studio.lunabee.arn.repository.UserRepository
import studio.lunabee.arn.util.AbsentLiveData
import studio.lunabee.arn.vo.Resource
import studio.lunabee.arn.vo.user.User
import javax.inject.Inject

class UserViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    private val _nick = MutableLiveData<String>()
    val nick: LiveData<String>
        get() = _nick
    val user: LiveData<Resource<List<User>>> = Transformations
        .switchMap(_nick) { id ->
            if (id == null) {
                AbsentLiveData.create()
            } else {
                userRepository.loadUserByNickName(id)
            }
        }

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