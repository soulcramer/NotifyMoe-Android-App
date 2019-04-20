package app.soulcramer.arn.db

import androidx.lifecycle.LiveData
import app.soulcramer.arn.vo.animelist.AnimeListItem
import app.soulcramer.arn.vo.animelist.AnimeListItemFields
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeListDao @Inject constructor(private val monarchy: Monarchy) {

    fun insert(userId: String, item: AnimeListItem) {
        monarchy.writeAsync {
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
        monarchy.writeAsync { it.insertOrUpdate(items) }
    }

    fun findByUserId(userId: String): LiveData<Monarchy.ManagedChangeSet<AnimeListItem>> {
        return monarchy.findAllManagedWithChanges { realm ->
            realm.where<AnimeListItem>().equalTo(AnimeListItemFields.USER_ID, userId)
        }
    }

    fun findByAnimeId(animeId: String): LiveData<List<AnimeListItem>> {
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<AnimeListItem>().equalTo(AnimeListItemFields.ANIME_ID, animeId)
        }
    }

    fun findById(id: String): LiveData<List<AnimeListItem>> {
        return monarchy.findAllCopiedWithChanges { realm ->
            realm.where<AnimeListItem>().equalTo(AnimeListItemFields.ID, id)
        }
    }
}
