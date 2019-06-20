package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserCache
import app.soulcramer.arn.data.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserCacheDataStoreTest {

    private lateinit var userCacheDataStore: UserCacheDataStore

    private lateinit var mockUserCache: UserCache

    @Before
    fun setUp() {
        mockUserCache = mockk()
        userCacheDataStore = UserCacheDataStore(mockUserCache)
    }

    // <editor-fold desc="Save User">
    @Test
    fun `Given a user When saving him Then save him and refresh cacheTime`() {
        runBlocking {
            val testUserEntity = UserFactory.makeUserEntity()
            stubUserCacheSaveUser()
            stubUserCacheLastCacheTime()

            userCacheDataStore.saveUser(testUserEntity)

            coVerify(exactly = 1) { mockUserCache.saveUser(any()) }
            coVerify(exactly = 1) { mockUserCache.setLastCacheTime(any()) }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Get User">
    @Test
    fun `Given a user id When getting him Then return user with id`() {
        runBlocking {
            val testUserEntity = UserFactory.makeUserEntity()
            stubUserCacheGetUser(testUserEntity)

            val userEntity = userCacheDataStore.getUser(testUserEntity.id)

            assertThat(userEntity).isEqualTo(testUserEntity)
        }
    }

    @Test
    fun `Given a user id of non existant user When getting corresponding user Then throw exception`() {
        runBlocking {
            val testUserEntity = UserFactory.makeUserEntity()
            coEvery { mockUserCache.getUser(any()) } throws NoSuchElementException()

            var userEntity: UserEntity? = null
            try {
                userEntity = userCacheDataStore.getUser(testUserEntity.id)
            } catch (e: NoSuchElementException) {
            }

            coVerify(exactly = 1) { mockUserCache.getUser(any()) }

            assertThat(userEntity).isNull()
        }
    }

    @Test
    fun `Given a empty user id When getting corresponding user Then throw exception`() {
        runBlocking {
            coEvery { mockUserCache.getUser(any()) } throws NoSuchElementException()

            var userEntity: UserEntity? = null
            try {
                userEntity = userCacheDataStore.getUser("")
            } catch (e: NoSuchElementException) {
            }

            coVerify(exactly = 1) { mockUserCache.getUser(any()) }

            assertThat(userEntity).isNull()
        }
    }
    // </editor-fold>

    // <editor-fold desc="Search Users">
    @Test
    fun `Given a user nickname When searching user with similar name them Then return list user`() {
        runBlocking {
            coEvery { mockUserCache.searchUsers(any()) } returns UserFactory.makeUserEntityList(4)

            val searchedUserEntities = userCacheDataStore.searchUsers("qzdq")

            assertThat(searchedUserEntities).hasSize(4)
        }
    }

    @Test
    fun `Given a non existant user nickname When searching user with similar name them Then return empty user`() {
        runBlocking {
            coEvery { mockUserCache.searchUsers(any()) } returns emptyList()

            val searchedUserEntities = userCacheDataStore.searchUsers("qzdqd")

            coVerify(exactly = 1) { mockUserCache.searchUsers(any()) }
            assertThat(searchedUserEntities).isEmpty()
        }
    }

    @Test
    fun `Given a empty user nickname When searching user with similar name them Then return empty list`() {
        runBlocking {
            coEvery { mockUserCache.searchUsers(any()) } returns UserFactory.makeUserEntityList(4)

            val searchedUserEntities = userCacheDataStore.searchUsers("")

            coVerify(exactly = 1) { mockUserCache.searchUsers(any()) }
            assertThat(searchedUserEntities).hasSize(4)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Stub helper methods">

    private fun stubUserCacheSaveUser() {
        coEvery { mockUserCache.saveUser(any()) } just Runs
    }

    private fun stubUserCacheGetUser(user: UserEntity) {
        coEvery { mockUserCache.getUser(any()) } returns user
    }

    private fun stubUserCacheLastCacheTime() {
        every { mockUserCache.setLastCacheTime(any()) } just Runs
    }
    // </editor-fold>
}