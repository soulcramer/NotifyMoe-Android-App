package studio.lunabee.arn.vo.anime

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Count(
    var overall: Int = 0,
    var story: Int = 0,
    var visuals: Int = 0,
    var soundtrack: Int = 0
) : RealmObject()
