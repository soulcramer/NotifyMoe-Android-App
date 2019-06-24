package app.soulcramer.arn.domain.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.domain.model.User

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUser(
            id: String = randomUuid(),
            name: String = randomUuid(),
            title: String = randomUuid(),
            avatar: String = randomUuid(),
            cover: String = randomUuid()
        ): User {
            return User(id, name, title, avatar, cover)
        }

        fun makeUserList(count: Int): List<User> {
            val users = mutableListOf<User>()
            repeat(count) {
                users.add(makeUser())
            }
            return users
        }

        fun makeUserListWithCloseNickname(count: Int, nickname: String = ""): List<User> {
            val users = mutableListOf<User>()
            repeat(count) {
                val name = nickname + randomUuid()
                users.add(makeUser(name = name))
            }
            return users
        }
    }
}