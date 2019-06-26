package app.soulcramer.arn.remote.model.user

import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

/**
 * Representation for a [UserModel] fetched from the API
 */
@JsonClass(generateAdapter = true)
class UserModel(
    val id: String,
    val nick: String,
    val role: String,
    val avatar: AvatarModel,
    val cover: CoverModel,
    val proExpires: OffsetDateTime?
)

@JsonClass(generateAdapter = true)
class AvatarModel(val extension: String)

@JsonClass(generateAdapter = true)
class CoverModel(val extension: String)