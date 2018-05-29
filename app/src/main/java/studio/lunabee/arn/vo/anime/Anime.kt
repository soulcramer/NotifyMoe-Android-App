package studio.lunabee.arn.vo.anime

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Anime(
    @field:PrimaryKey
    var id: String = "",
    var type: String = "",
    var title: Title? = null,
    var summary: String = "",
    var status: String = "",
    var genres: RealmList<String> = RealmList(),
    var startDate: String = "",
    var endDate: String = "",
    var episodeCount: Int = 1,
    var episodeLength: Int = 1,
    var source: String = "",
    var image: Image? = null,
    var rating: Rating? = null,
    var notes: String = "",
    var rewatch: Int = 0,
    var created: String = "",
    var edited: String = ""
) : RealmObject()
