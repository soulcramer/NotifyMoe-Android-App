package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserRemote
import app.soulcramer.arn.data.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRemoteDataStoreTest {

    private lateinit var userRemoteDataStore: UserRemoteDataStore

    private lateinit var mockUserRemote: UserRemote

    @Before
    fun setUp() {
        mockUserRemote = mockk()
        userRemoteDataStore = UserRemoteDataStore(mockUserRemote)
    }

    // <editor-fold desc="Save Users">
    @Test(expected = UnsupportedOperationException::class)
    fun `Given user to save When saving user Then return throw exception`() {
        runBlocking {
            userRemoteDataStore.saveUser(UserFactory.makeUserEntity())
        }
    }
    // </editor-fold>

    // <editor-fold desc="Get Users">
    @Test
    fun `Given a user id of existant user When getting corresponding user Then return user`() {
        runBlocking {
            val testUserEntity = UserFactory.makeUserEntity()
            stubUserCacheGetUser(testUserEntity)

            val user = mockUserRemote.getUser(testUserEntity.id)

            assertThat(user).isEqualTo(testUserEntity)
        }
    }

    @Test(expected = NoSuchElementException::class)
    fun `Given a user id of non existant user When getting corresponding user Then throw exception`() {
        runBlocking {
            coEvery { mockUserRemote.getUser(any()) } throws NoSuchElementException()

            userRemoteDataStore.getUser("")
        }
    }

    @Test(expected = NoSuchElementException::class)
    fun `Given a empty user id When getting corresponding user Then throw exception`() {
        runBlocking {
            coEvery { mockUserRemote.getUser(any()) } throws NoSuchElementException()

            userRemoteDataStore.getUser("")
        }
    }
    // </editor-fold>

    // <editor-fold desc="Stub helper methods">
    private fun stubUserCacheGetUser(user: UserEntity) {
        coEvery { mockUserRemote.getUser(any()) } returns user
    }
    // </editor-fold>
}