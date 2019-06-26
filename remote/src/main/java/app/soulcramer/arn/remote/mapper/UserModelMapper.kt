package app.soulcramer.arn.remote.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.remote.model.user.AvatarModel
import app.soulcramer.arn.remote.model.user.CoverModel
import app.soulcramer.arn.remote.model.user.UserModel
import app.soulcramer.arn.remote.model.user.graphql.GraphQlUserModel

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class UserModelMapper : EntityMapper<GraphQlUserModel, UserModel> {

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapFromRemote(type: GraphQlUserModel): UserModel {
        return UserModel(
            type.ID,
            type.Nick,
            type.Role,
            AvatarModel(type.Avatar.Extension),
            CoverModel(type.Cover.Extension),
            type.ProExpires
        )
    }
}