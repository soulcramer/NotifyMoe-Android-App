package app.soulcramer.arn.remote.model.user.graphql

import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

/**
 * Representation for a [GraphQlUserModel] fetched from the API
 */
@JsonClass(generateAdapter = true)
class GraphQlUserModel(
    val ID: String,
    val Nick: String,
    val Role: String,
    val Avatar: GraphQlAvatarModel,
    val Cover: GraphQlCoverModel,
    val ProExpires: OffsetDateTime?
)

@JsonClass(generateAdapter = true)
class GraphQlAvatarModel(val Extension: String)

@JsonClass(generateAdapter = true)
class GraphQlCoverModel(val Extension: String)