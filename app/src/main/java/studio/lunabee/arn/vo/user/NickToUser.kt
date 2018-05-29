package studio.lunabee.arn.vo.user

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class NickToUser(
    @field:PrimaryKey
    var userId: String = "",
    @field:SerializedName("nick")
    var nickName: String = ""
) : RealmObject()
