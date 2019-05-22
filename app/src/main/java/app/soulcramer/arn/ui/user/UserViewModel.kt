package app.soulcramer.arn.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import app.soulcramer.arn.model.Resource
import app.soulcramer.arn.model.user.User
import app.soulcramer.arn.util.AbsentLiveData

class UserViewModel(userRepository: UserRepository) : ViewModel() {
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    val user: LiveData<Resource<User>> = getUserLiveData(userRepository)

    val userId: LiveData<String> = Transformations.switchMap(user) { resource ->
        MutableLiveData<String>().also {
            it.value = resource.data?.id

        }
    }

    private fun getUserLiveData(
        userRepository: UserRepository): LiveData<Resource<User>> {
        return Transformations.switchMap(_nickname) { nick ->
            if (nick == null) {
                AbsentLiveData.create()
            } else {
                userRepository.loadUserByNickName(nick)
            }
        }
    }

    fun setNickname(nickname: String?) {
        if (_nickname.value != nickname) {
            _nickname.value = nickname
        }
    }

    fun retry() {
        _nickname.value?.let {
            _nickname.value = it
        }
    }
}
