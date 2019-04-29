package app.soulcramer.arn.vo.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NickToUser(
    @PrimaryKey
    var userId: String,
    @SerializedName("nick")
    var nickName: String
)
