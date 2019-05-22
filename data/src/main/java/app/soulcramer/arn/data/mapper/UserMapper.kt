package app.soulcramer.arn.data.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.domain.model.User

/**
 * Map a [UserEntity] to and from a [User] instance when data is moving between
 * this later and the Domain layer
 */
open class UserMapper : Mapper<UserEntity, User> {

    /**
     * Map a [UserEntity] instance to a [User] instance
     */
    override fun mapFromEntity(type: UserEntity): User {
        return User(type.id, type.name, type.title, type.avatar, type.cover)
    }

    /**
     * Map a [User] instance to a [UserEntity] instance
     */
    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(type.id, type.name, type.title, type.avatar, type.cover)
    }

}