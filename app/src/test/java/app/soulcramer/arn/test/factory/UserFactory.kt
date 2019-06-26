package app.soulcramer.arn.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.domain.model.User
import java.time.OffsetDateTime

/**
 * Factory class for User related instances
 */
object UserFactory {
    fun makeUser(
        id: String = randomUuid(),
        name: String = randomUuid(),
        role: String = randomUuid(),
        proExpiresDate: OffsetDateTime = OffsetDateTime.now(),
        hasAvatar: Boolean = DataFactory.randomBoolean(),
        hasCover: Boolean = DataFactory.randomBoolean()
    ): User {
        return User(id, name, role, proExpiresDate, hasAvatar, hasCover)
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