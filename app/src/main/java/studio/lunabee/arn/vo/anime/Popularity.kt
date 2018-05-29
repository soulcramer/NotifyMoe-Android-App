package studio.lunabee.arn.vo.anime

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Popularity(
    var watching: Int = 0,
    var completed: Int = 0,
    var planned: Int = 0,
    var hold: Int = 0,
    var dropped: Int = 0
) : RealmObject()
