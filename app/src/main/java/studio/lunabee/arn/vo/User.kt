package studio.lunabee.arn.vo

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
    var id: String? = null
    @field:SerializedName("nick")
    @field:Index
    var nickName: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var role: String? = null
}