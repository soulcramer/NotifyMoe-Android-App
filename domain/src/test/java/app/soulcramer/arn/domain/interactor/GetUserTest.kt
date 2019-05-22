package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.repository.UserRepository
import app.soulcramer.arn.model.Result.Error
import app.soulcramer.arn.model.Result.Success
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
            val result = getUser.executeNow("")
            assertThat(result).isInstanceOf(Error::class.java)
        }
    }

    @Test
    fun `Given userID for inexistant user When getting the user by id Then return error`() {
        runBlocking {
            val result = getUser.executeNow("234")
            assertThat(result).isInstanceOf(Error::class.java)
        }
    }

    @Test
    fun `Given existant user When getting the user by id Then return user`() {
        runBlocking {
            val result = getUser.executeNow("1")
            assertThat(result).isInstanceOf(Success::class.java)

            val user = (result as Success).data
            assertThat(user.name).isEqualTo("user 1")
        }
    }
}