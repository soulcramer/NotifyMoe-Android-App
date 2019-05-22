package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository

class UserTestRepository : UserRepository {

    private val data = mapOf(
        "1" to User("user 1", "user 1", "user 1", "user 1"),
        "2" to User("user 2", "user 2", "user 2", "user 2")
    )

    override fun getUser(userId: String): User {
        val user = data[userId]
        checkNotNull(user)
        return user
    }

}