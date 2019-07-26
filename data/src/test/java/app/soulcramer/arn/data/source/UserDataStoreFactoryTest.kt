package app.soulcramer.arn.data.source

import app.soulcramer.arn.data.repository.UserCache
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDataStoreFactoryTest {

    private lateinit var userDataStoreFactory: UserDataStoreFactory

    private lateinit var mockUserCache: UserCache
    private lateinit var mockUserCacheDataStore: UserCacheDataStore
    private lateinit var mockUserRemoteDataStore: UserRemoteDataStore

    @Before
    fun setUp() {
        mockUserCache = mockk()
        mockUserCacheDataStore = mockk()
        mockUserRemoteDataStore = mockk()
        userDataStoreFactory = UserDataStoreFactory(mockUserCache, mockUserCacheDataStore, mockUserRemoteDataStore)
    }

    // <editor-fold desc="Retrieve Data Store">
    @Test
    fun `Given user not cached When getting data store Then return remote data store`() {
        runBlocking {
            stubUserCacheIsCached(false)

            val userDataStore = userDataStoreFactory.retrieveDataStore()

            assertThat(userDataStore).isInstanceOf(UserRemoteDataStore::class.java)
        }
    }

    @Test
    fun `Given user cached and cache expired When getting data store Then return remote data store`() {
        runBlocking {
            stubUserCacheIsCached(true)
            stubUserCacheIsExpired(true)

            val userDataStore = userDataStoreFactory.retrieveDataStore()

            assertThat(userDataStore).isInstanceOf(UserRemoteDataStore::class.java)
        }
    }

    @Test
    fun `Given user cached and cache not expired When getting data store Then return cache data store`() {
        runBlocking {
            stubUserCacheIsCached(true)
            stubUserCacheIsExpired(false)

            val userDataStore = userDataStoreFactory.retrieveDataStore()

            assertThat(userDataStore).isInstanceOf(UserCacheDataStore::class.java)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun `Given userDataStoreFactory When remote data store Then return remote data store`() {
        runBlocking {
            val userDataStore = userDataStoreFactory.retrieveRemoteDataStore()

            assertThat(userDataStore).isInstanceOf(UserRemoteDataStore::class.java)
            assertThat(userDataStore).isSameInstanceAs(mockUserRemoteDataStore)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun `Given userDataStoreFactory When cache data store Then return cache data store`() {
        runBlocking {
            val userDataStore = userDataStoreFactory.retrieveCacheDataStore()

            assertThat(userDataStore).isInstanceOf(UserCacheDataStore::class.java)
            assertThat(userDataStore).isSameInstanceAs(mockUserCacheDataStore)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Stub helper methods">
    private fun stubUserCacheIsCached(isCached: Boolean) {
        coEvery { mockUserCache.isCached() } returns isCached
    }

    private fun stubUserCacheIsExpired(isExpired: Boolean) {
        coEvery { mockUserCache.isExpired() } returns isExpired
    }
    // </editor-fold>
}