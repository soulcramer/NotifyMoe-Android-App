package app.soulcramer.arn.domain.model

import app.soulcramer.arn.data.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUser(): User {
            return User(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
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