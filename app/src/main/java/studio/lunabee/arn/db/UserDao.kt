package studio.lunabee.arn.db

import android.arch.lifecycle.LiveData
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import studio.lunabee.arn.vo.user.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDao @Inject constructor(private val monarchy: Monarchy) {

    fun insert(user: User) {
        monarchy.runTransactionSync { it.copyToRealmOrUpdate(user) }
    }

    fun findByNick(nickname: String): LiveData<List<User>> {
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<User>().like("nickName", nickname)
        }
    }

    fun findById(id: String): LiveData<List<User>> {
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<User>().equalTo("id", id)
        }
    }
}