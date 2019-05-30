package app.soulcramer.arn.cache.mapper

import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.data.model.UserEntity

/**
 * Map a [CachedUser] instance to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
class UserEntityMapper : EntityMapper<CachedUser, UserEntity> {

    /**
     * Map a [UserEntity] instance to a [CachedUser] instance
     */
    override fun mapToCached(type: UserEntity): CachedUser {
        return CachedUser(type.id, type.nickname, type.role, type.avatar, type.cover)
    }

    /**
     * Map a [CachedUser] instance to a [UserEntity] instance
     */
    override fun mapFromCached(type: CachedUser): UserEntity {
        return UserEntity(type.id, type.name, type.title, type.avatar, type.cover)
    }

}