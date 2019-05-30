package app.soulcramer.arn.remote.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.remote.model.UserModel

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class UserEntityMapper : EntityMapper<UserModel, UserEntity> {

    val avatarImageUrl = "https://media.notify.moe/images/avatars/large/"
    val coverImageUrl = "https://media.notify.moe/images/covers/large/"

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapFromRemote(type: UserModel): UserEntity {
        return UserEntity(type.id,
            type.nick,
            type.role,
            "$avatarImageUrl${type.id}.webp",
            "$coverImageUrl${type.id}.webp")
    }

}