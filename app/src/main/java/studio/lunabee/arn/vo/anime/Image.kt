package studio.lunabee.arn.vo.anime

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Image(
    var extension: String = "",
    var width: String = "",
    var height: String = "",
    var averageColor: AverageColor? = null,
    var lastModified: String = ""
) : RealmObject()
