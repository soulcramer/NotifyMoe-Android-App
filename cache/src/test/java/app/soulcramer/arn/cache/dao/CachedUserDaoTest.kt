package app.soulcramer.arn.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.soulcramer.arn.cache.NotifyMoeDatabase
import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.cache.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
open class CachedUserDaoTest {

    private lateinit var notifyMoeDatabase: NotifyMoeDatabase

    @Before
    fun initDb() {
        notifyMoeDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotifyMoeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        notifyMoeDatabase.close()
    }

    @Test
    fun `Given users to save When saving them Then all users should be saved`() {
        runBlocking {
            val cachedUser = UserFactory.makeCachedUser()
            notifyMoeDatabase.userDao().insertUsers(cachedUser)

            val users = notifyMoeDatabase.userDao().getAll()
            assertThat(users).isNotEmpty()
        }
    }

    @Test
    fun `Given saved users in db When getting all them Then return all cached users ordered by name`() {
        runBlocking {
            val cachedUsers = UserFactory.makeCachedUserList(5)

            cachedUsers.forEach {
                notifyMoeDatabase.userDao().insertUsers(it)
            }

            val retrievedUsers = notifyMoeDatabase.userDao().getAll()
            assertThat(retrievedUsers).isEqualTo(
                cachedUsers.sortedWith(compareBy({ it.name }, { it.name }))
            )
        }
    }

    // <editor-fold desc="Get Users">
    @Test
    fun `Given saved users in db When searching with similar name Then return all cached users with the nickname`() {
        runBlocking {
            val nickname = "abcd"
            val cachedUsers = UserFactory.makeCachedUserList(2).toMutableList()
            val similarNameUsers = UserFactory.makeCachedUserListWithCloseNickname(2, nickname)
            cachedUsers += similarNameUsers
            cachedUsers.forEach { cachedUser: CachedUser ->
                notifyMoeDatabase.userDao().insertUsers(cachedUser)
            }

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname(nickname)
            assertThat(retrievedUsers).isEqualTo(
                similarNameUsers.sortedWith(compareBy({ it.name }, { it.name }))
            )
        }
    }

    @Test
    fun `Given saved users in db with empty nickname When searching with similar name Then return all users`() {
        runBlocking {
            val nickname = "abcd"
            val cachedUsers = UserFactory.makeCachedUserList(2).toMutableList()
            val similarNameUsers = UserFactory.makeCachedUserListWithCloseNickname(2, nickname)
            cachedUsers += similarNameUsers
            cachedUsers.forEach { cachedUser: CachedUser ->
                notifyMoeDatabase.userDao().insertUsers(cachedUser)
            }

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname("")
            assertThat(retrievedUsers).isEqualTo(
                cachedUsers.sortedWith(compareBy({ it.name }, { it.name }))
            )
        }
    }

    @Test
    fun `Given saved users in db without similar nickname When searching with similar name Then return empty list`() {
        runBlocking {
            val cachedUsers = UserFactory.makeCachedUserList(4).toMutableList()
            cachedUsers.forEach { cachedUser: CachedUser ->
                notifyMoeDatabase.userDao().insertUsers(cachedUser)
            }

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname("qzdqzd")
            assertThat(retrievedUsers).isNotEqualTo(
                cachedUsers.sortedWith(compareBy({ it.name }, { it.name }))
            )
            assertThat(retrievedUsers).isEmpty()
        }
    }
    // </editor-fold>
}