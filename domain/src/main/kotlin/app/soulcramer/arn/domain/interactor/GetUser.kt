package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.domain.repository.UserRepository

/**
 * Use case used for retreiving a [User] instances from the [UserRepository]
 */
open class GetUser(val userRepository: UserRepository) : UseCase<String, User>() {
    override fun execute(parameters: String): User = userRepository.getUser(parameters)
}