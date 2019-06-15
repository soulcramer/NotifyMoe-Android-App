package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository
import app.soulcramer.arn.domain.test.factory.UserFactory

class UserTestRepository : UserRepository {

    val users = UserFactory.makeUserList(2)

    private val data = users.associate {
        it.id to it
    }

    override suspend fun getUser(userId: String): User {
        val user = data[userId]
        checkNotNull(user)
        return user
    }
}