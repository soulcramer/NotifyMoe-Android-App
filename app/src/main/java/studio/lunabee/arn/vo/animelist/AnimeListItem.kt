package studio.lunabee.arn.vo.animelist

import com.google.gson.annotations.Expose
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class AnimeListItem(@field:PrimaryKey @field:Expose(serialize = false) var id: String = "", @field:Required var userId: String = "", @field:Required var animeId: String = "", @field:Index var status: String = "",
    var episodes: Int = 0, var rating: Rating? = null, var notes: String = "", var rewatch: Int = 0,
    var created: String = "", var edited: String = "") : RealmObject() {

    fun compoundPrimaryKey() {
        if (!isManaged) {
            id = "$userId$animeId"
        }
    }
}