package studio.lunabee.arn.db

import android.arch.lifecycle.LiveData
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import studio.lunabee.arn.vo.anime.Anime
import studio.lunabee.arn.vo.anime.AnimeFields
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeDao @Inject constructor(private val monarchy: Monarchy) {

    fun insert(item: Anime) {
        monarchy.runTransactionSync {
            it.insertOrUpdate(item)
        }
    }

    fun insert(userId: String, items: List<Anime>) {
        monarchy.runTransactionSync { it.insertOrUpdate(items) }
    }

    fun findById(id: String): LiveData<List<Anime>> {
        return monarchy.findAllCopiedWithChanges({ realm ->
            realm.where<Anime>().equalTo(AnimeFields.ID, id)
        })
    }
}
