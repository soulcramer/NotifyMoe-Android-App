package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.repository.UserRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Ignore

@Ignore("Waiting to find how to test datasource")
class SearchUsersTest {

    private lateinit var searchUsers: SearchUsers

    private lateinit var testUserRepository: UserRepository

    @Before
    fun setUp() {
        testUserRepository = mockk()
        searchUsers = SearchUsers(testUserRepository)
    }

    /*@Test
    fun `Given empty search nickname When searching corresponding the user Then return all users`() = runBlocking {
        coEvery { testUserRepository.searchUser("") } returns UserFactory.makeUserList(5,
            "Scott")

        val result = searchUsers("")

        assertThat(result).isInstanceOf(Success::class.java)
        val users = (result as Success).data

        assertThat(users).hasSize(5)
    }

    @Test
    fun `Given a search nickname When searching non existent users Then return empty list`() = runBlocking {
        coEvery { testUserRepository.searchUser(any()) } returns emptyList()

        val result = searchUsers("qzdqzd")

        assertThat(result).isInstanceOf(Success::class.java)
        val users = (result as Success).data

        assertThat(users).isEmpty()
    }

    @Test
    fun `Given a search nickname When searching existent users Then return list of users`() = runBlocking {
        val searchedNickname = "abcd"
        coEvery {
            testUserRepository.searchUser(searchedNickname, parameters.forceRefresh)
        } returns UserFactory.makeUserList(5, searchedNickname)

        val result = searchUsers(searchedNickname)

        assertThat(result).isInstanceOf(Success::class.java)
        val users = (result as Success).data

        assertThat(users).hasSize(5)
    }*/
}