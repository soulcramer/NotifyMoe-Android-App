package app.soulcramer.arn.vo.anime

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Rating(
    var overall: Double = 0.0,
    var story: Double = 0.0,
    var visuals: Double = 0.0,
    var soundtrack: Double = 0.0,
    var count: Count? = null
) : RealmObject()
