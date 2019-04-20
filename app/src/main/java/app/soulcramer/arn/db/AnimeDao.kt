package app.soulcramer.arn.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.soulcramer.arn.vo.anime.Anime
import app.soulcramer.arn.vo.anime.AnimeFields
import com.zhuinden.monarchy.Monarchy
import io.realm.kotlin.where

class AnimeDao(private val monarchy: Monarchy) {

    fun insert(item: Anime) {
        monarchy.runTransactionSync { it.insertOrUpdate(item) }
    }

    fun insert(items: List<Anime>) {
        monarchy.runTransactionSync { it.insertOrUpdate(items) }
    }

    fun findById(id: String): LiveData<Anime> {
        val anime = monarchy.findAllCopiedWithChanges { realm ->
            realm.where<Anime>().equalTo(AnimeFields.ID, id)
        }
        return Transformations.map(anime) {
            it.firstOrNull()
        }
    }

    fun findByIdSync(id: String): Anime? {
        val anime: Anime? = null
        monarchy.runTransactionSync { it.where<Anime>().equalTo(AnimeFields.ID, id) }
        return anime
    }
}
