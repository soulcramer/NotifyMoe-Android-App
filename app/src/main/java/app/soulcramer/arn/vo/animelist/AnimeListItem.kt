package app.soulcramer.arn.vo.animelist

import app.soulcramer.arn.vo.anime.Anime
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class AnimeListItem(
    @field:PrimaryKey
    @field:Expose(serialize = false)
    var id: String = "",
    @field:Required
    var userId: String = "",
    @field:Required
    var animeId: String = "",
    var anime: Anime? = null,
    @field:Index
    var status: String = "",
    var episodes: Int = 0,
    @field:SerializedName("rating")
    var userRating: UserRating? = null,
    var notes: String = "",
    var rewatch: Int = 0,
    var created: String = "",
    var edited: String = ""
) : RealmObject() {

    fun compoundPrimaryKey() {
        if (!isManaged) {
            id = "$userId$animeId"
        }
    }
}
