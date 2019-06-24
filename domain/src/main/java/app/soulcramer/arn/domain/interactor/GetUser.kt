package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository

/**
 * Use case used for retrieving a [User] instances from the [UserRepository]
 */
class GetUser(private val userRepository: UserRepository) : UseCase<String, User>() {
    override suspend fun execute(parameters: String): User = userRepository.getUser(parameters)
}