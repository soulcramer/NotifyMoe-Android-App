package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.repository.UserRepository
import app.soulcramer.arn.domain.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUserTest {

    private lateinit var getUser: GetUser

    private lateinit var mockUserRepository: UserRepository

    @Before
    fun setUp() {
        mockUserRepository = mockk()
        getUser = GetUser(mockUserRepository)
    }

    @Test
    fun `Given empty userId When getting the user by id Then return error`() {
        runBlocking {
            coEvery { mockUserRepository.getUser(any()) } throws NoSuchElementException()
            val result = getUser("")
            assertThat(result).isInstanceOf(Failure::class.java)
        }
    }

    @Test
    fun `Given userID for inexistant user When getting the user by id Then return error`() {
        runBlocking {
            coEvery { mockUserRepository.getUser(any()) } throws NoSuchElementException()
            val result = getUser("234")
            assertThat(result).isInstanceOf(Failure::class.java)
        }
    }

    @Test
    fun `Given existant user When getting the user by id Then return user`() {
        runBlocking {
            val testUser = UserFactory.makeUser()
            coEvery { mockUserRepository.getUser(testUser.id) } returns testUser
            val result = getUser(testUser.id)
            assertThat(result).isInstanceOf(Success::class.java)

            val user = (result as Success).data
            assertThat(user.nickname).isEqualTo(testUser.nickname)
        }
    }
}