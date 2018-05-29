package studio.lunabee.arn.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import studio.lunabee.arn.vo.user.User
import studio.lunabee.arn.vo.user.UserFields
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDao @Inject constructor(private val monarchy: Monarchy) {

    fun insert(user: User) {
        monarchy.runTransactionSync { it.copyToRealmOrUpdate(user) }
    }

    fun findByNick(nickname: String): LiveData<User> {
        val usersLiveData = monarchy.findAllCopiedWithChanges { realm ->
            realm.where<User>().like(UserFields.NICK_NAME, nickname)
        }
        return Transformations.map(usersLiveData) {
            it.firstOrNull()
        }
    }

    fun findById(id: String): LiveData<User> {
        val usersLiveData = monarchy.findAllCopiedWithChanges { realm ->
            realm.where<User>().equalTo(UserFields.ID, id)
        }
        return Transformations.map(usersLiveData) {
            it.firstOrNull()
        }
    }
}
