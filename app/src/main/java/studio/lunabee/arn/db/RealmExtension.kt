@file:JvmName("RealmUtils")

// pretty name for utils class if called from Java
package studio.lunabee.arn.db

import com.zhuinden.monarchy.Monarchy
import io.realm.RealmModel
import io.realm.RealmResults
import studio.lunabee.arn.common.LiveRealmData

fun Monarchy.userDao(): UserDao = UserDao(this)

// Convenience extension on RealmResults to return as LiveRealmData
fun <T : RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)