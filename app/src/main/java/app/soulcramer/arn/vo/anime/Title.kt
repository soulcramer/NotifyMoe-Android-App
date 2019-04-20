package app.soulcramer.arn.vo.anime

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Title(
    var canonical: String = "",
    var romaji: String = "",
    var english: String = "",
    var japanese: String = "",
    var hiragana: String = "",
    var synonyms: RealmList<String> = RealmList()
) : RealmObject()
