package app.soulcramer.arn.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    @SerializedName("nick")
    val nickName: String,
    val role: String
)
