package studio.lunabee.arn.vo.user

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.RealmClass

@RealmClass
open class NickToUser(
    @field:SerializedName("nick")
    var nickName: String = "",
    @field:Index
    var userId: String = ""
) : RealmObject()
