package studio.lunabee.arn.vo.user

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class User : RealmObject() {
    @field:PrimaryKey
    @field:Required
    var id: String = ""
    @field:SerializedName("nick")
    @field:Index
    var nickName: String = ""
    var role: String = ""
}
