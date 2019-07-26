package app.soulcramer.arn.domain.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomBoolean
import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.domain.model.User
import java.time.OffsetDateTime

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUser(
            id: String = randomUuid(),
            name: String = randomUuid(),
            title: String = randomUuid(),
            proExpiresDate: OffsetDateTime = OffsetDateTime.now(),
            hasAvatar: Boolean = randomBoolean(),
            hasCover: Boolean = randomBoolean()
        ): User {
            return User(id, name, title, proExpiresDate, hasAvatar, hasCover)
        }

        fun makeUserList(
            count: Int,
            nickname: String = "",
            hasAvatar: Boolean = true,
            hasCover: Boolean = true
        ): List<User> {
            val users = mutableListOf<User>()
            repeat(count) {
                val name = nickname + randomUuid()
                users.add(makeUser(name = name, hasAvatar = hasAvatar, hasCover = hasCover))
            }
            return users
        }
    }
}