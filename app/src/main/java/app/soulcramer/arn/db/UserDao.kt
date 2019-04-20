package app.soulcramer.arn.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.soulcramer.arn.vo.user.User
import app.soulcramer.arn.vo.user.UserFields
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where

class UserDao(private val monarchy: Monarchy) {

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
