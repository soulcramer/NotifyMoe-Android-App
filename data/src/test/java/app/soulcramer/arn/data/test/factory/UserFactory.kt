package app.soulcramer.arn.data.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.domain.model.User

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUserEntity(): UserEntity {
            return UserEntity(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeUser(): User {
            return User(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeUserEntityList(count: Int): List<UserEntity> {
            val bufferooEntities = mutableListOf<UserEntity>()
            repeat(count) {
                bufferooEntities.add(makeUserEntity())
            }
            return bufferooEntities
        }

        fun makeUserList(count: Int): List<User> {
            val bufferoos = mutableListOf<User>()
            repeat(count) {
                bufferoos.add(makeUser())
            }
            return bufferoos
        }

    }

}