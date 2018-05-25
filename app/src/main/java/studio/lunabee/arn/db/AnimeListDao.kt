package studio.lunabee.arn.db

import android.arch.lifecycle.LiveData
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import studio.lunabee.arn.vo.animelist.AnimeListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeListDao @Inject constructor(private val monarchy: Monarchy) {

    fun insert(userId: String, item: AnimeListItem) {
        monarchy.runTransactionSync {
            item.userId = userId
            item.compoundPrimaryKey()
            it.insertOrUpdate(item)
        }
    }

    fun insert(userId: String, items: List<AnimeListItem>) {
        items.forEach {
            it.userId = userId
            it.compoundPrimaryKey()
        }
        monarchy.runTransactionSync { it.insertOrUpdate(items) }
    }

    fun findByUserId(userId: String): LiveData<List<AnimeListItem>> {
        return monarchy.findAllCopiedWithChanges({ realm ->
            realm.where<AnimeListItem>().equalTo("userId", userId)
        })
    }

    fun findByAnimeId(animeId: String): LiveData<List<AnimeListItem>> {
        return monarchy.findAllCopiedWithChanges({ realm ->
            realm.where<AnimeListItem>().equalTo("animeId", animeId)
        })
    }

    fun findById(id: String): LiveData<List<AnimeListItem>> {
        return monarchy.findAllCopiedWithChanges({ realm ->
            realm.where<AnimeListItem>().equalTo("id", id)
        })
    }
}