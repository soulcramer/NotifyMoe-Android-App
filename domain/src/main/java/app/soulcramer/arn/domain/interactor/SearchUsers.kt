package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository

/**
 * Use case used for searching a list of [User] instances by their nickname from the [UserRepository]
 */
class SearchUsers(val userRepository: UserRepository) : UseCase<String, List<User>>() {
    override suspend fun execute(parameters: String): List<User> = userRepository.searchUser(parameters)
}