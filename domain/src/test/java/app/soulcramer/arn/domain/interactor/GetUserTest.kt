package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserTest {

    private lateinit var getUser: GetUser

    private lateinit var testUserRepository: UserRepository

    @Before
    fun setUp() {
        testUserRepository = UserTestRepository()
        getUser = GetUser(testUserRepository)
    }

    @Test
    fun `Given empty userId When getting the user by id Then return error`() {
        runBlocking {
            val result = getUser("")
            assertThat(result).isInstanceOf(Failure::class.java)
        }
    }

    @Test
    fun `Given userID for inexistant user When getting the user by id Then return error`() {
        runBlocking {
            val result = getUser("234")
            assertThat(result).isInstanceOf(Failure::class.java)
        }
    }

    @Test
    fun `Given existant user When getting the user by id Then return user`() {
        runBlocking {
            val testUser = (testUserRepository as UserTestRepository).users[0]
            val result = getUser(testUser.id)
            assertThat(result).isInstanceOf(Success::class.java)

            val user = (result as Success).data
            assertThat(user.name).isEqualTo(testUser.name)
        }
    }
}