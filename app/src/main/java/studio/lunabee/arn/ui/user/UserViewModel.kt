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
    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    val user: LiveData<Resource<User>> = getUserLiveData(userRepository)

    val userId: LiveData<String> = Transformations.switchMap(user) { resource ->
        MutableLiveData<String>().also {
            it.value = when (resource) {
                is Resource.Success -> resource.data?.id
                is Resource.Failure -> null
                is Resource.Loading -> null
            }
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
