package studio.lunabee.arn.vo

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Rating(var overall: Float = 0f, var story: Float = 0f, var visuals: Float = 0f,
    var soundtrack: Float = 0f) : RealmObject()