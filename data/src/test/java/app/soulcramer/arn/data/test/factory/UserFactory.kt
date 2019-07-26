package app.soulcramer.arn.data.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomBoolean
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.domain.model.User
import java.time.OffsetDateTime

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUserEntity(): UserEntity {
            return UserEntity(
                randomUuid(),
                randomUuid(),
                randomUuid(),
                OffsetDateTime.now(),
                randomBoolean(),
                randomBoolean()
            )
        }

        fun makeUser(): User {
            return User(
                randomUuid(),
                randomUuid(),
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

        fun makeUserList(count: Int): List<User> {
            val users = mutableListOf<User>()
            repeat(count) {
                users.add(makeUser())
            }
            return users
        }
    }
}