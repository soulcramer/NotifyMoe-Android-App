package app.soulcramer.arn.cache.test.factory

import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomBoolean
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.data.model.UserEntity
import java.time.OffsetDateTime

/**
 * Factory class for User related instances
 */
object UserFactory {

    fun makeCachedUser(nickname: String = randomUuid()): CachedUser {
        return CachedUser(
            randomUuid(),
            nickname,
            randomUuid(),
            OffsetDateTime.now(),
            randomBoolean(),
            randomBoolean()
        )
    }

    fun makeUserEntity(nickname: String = randomUuid()): UserEntity {
        return UserEntity(
            randomUuid(),
            nickname,
            randomUuid(),
            OffsetDateTime.now(),
            randomBoolean(),
            randomBoolean()
        )
    }

    fun makeUserEntityList(count: Int): List<UserEntity> {
        val userEntities = mutableListOf<UserEntity>()
        repeat(count) {
            userEntities.add(makeUserEntity())
        }
        return userEntities
    }

    fun makeCachedUserList(count: Int): List<CachedUser> {
        val cachedUsers = mutableListOf<CachedUser>()
        repeat(count) {
            cachedUsers.add(makeCachedUser())
        }
        return cachedUsers
    }

    fun makeUserListWithCloseNickname(count: Int, nickname: String = ""): List<UserEntity> {
        val users = mutableListOf<UserEntity>()
        repeat(count) {
            val name = nickname + randomUuid()
            users.add(makeUserEntity(nickname = name))
        }
        return users
    }

    fun makeCachedUserListWithCloseNickname(count: Int, nickname: String = ""): List<CachedUser> {
        val users = mutableListOf<CachedUser>()
        repeat(count) {
            val name = nickname + randomUuid()
            users.add(makeCachedUser(nickname = name))
        }
        return users
    }
}