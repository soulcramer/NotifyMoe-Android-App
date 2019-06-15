package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserCache
import app.soulcramer.arn.data.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserCacheDataStoreTest {

    private lateinit var userCacheDataStore: UserCacheDataStore

    private lateinit var userCache: UserCache

    @Before
    fun setUp() {
        userCache = mockk()
        userCacheDataStore = UserCacheDataStore(userCache)
    }

    //<editor-fold desc="Save User">
    @Test
    fun saveUserCompletes() {
        val testUserEntity = UserFactory.makeUserEntity()
        stubUserCacheSaveUser()
        stubUserCacheLastCacheTime()

        userCacheDataStore.saveUser(testUserEntity)
    }
    //</editor-fold>

    //<editor-fold desc="Get User">
    @Test
    fun getUserCompletes() {
        val testUserEntity = UserFactory.makeUserEntity()
        stubUserCacheGetUser(testUserEntity)
        val userEntity = userCacheDataStore.getUser(testUserEntity.id)
        assertThat(userEntity).isEqualTo(testUserEntity)

    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">

    private fun stubUserCacheSaveUser() {
        every { userCache.saveUser(any()) } just Runs
    }

    private fun stubUserCacheGetUser(user: UserEntity) {
        coEvery { userCache.getUser(any()) } returns user
    }

    private fun stubUserCacheLastCacheTime() {
        every { userCache.setLastCacheTime(any()) } just Runs
    }
    //</editor-fold>
}