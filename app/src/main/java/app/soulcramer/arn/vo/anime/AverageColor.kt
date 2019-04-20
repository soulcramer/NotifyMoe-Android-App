package app.soulcramer.arn.vo.anime

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class AverageColor(
    var hue: Double = 0.0,
    var saturation: Double = 0.0,
    var lightness: Double = 0.0
) : RealmObject()
