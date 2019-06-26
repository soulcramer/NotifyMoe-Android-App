package app.soulcramer.arn.cache.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.soulcramer.arn.cache.NotifyMoeDatabase
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
            notifyMoeDatabase.userDao().insertUser(cachedUser)

            val users = notifyMoeDatabase.userDao().getAll()
            assertThat(users).isNotEmpty()
        }
    }

    @Test
    fun `Given saved users in db When getting all them Then return all cached users ordered by name`() {
        runBlocking {
            val cachedUsers = UserFactory.makeCachedUserList(5)

            notifyMoeDatabase.userDao().insertUsers(cachedUsers)

            val retrievedUsers = notifyMoeDatabase.userDao().getAll()
            assertThat(retrievedUsers).isEqualTo(
                cachedUsers.sortedWith(compareBy({ it.nickname }, { it.nickname }))
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
            notifyMoeDatabase.userDao().insertUsers(cachedUsers)

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname(nickname)
            assertThat(retrievedUsers).isEqualTo(
                similarNameUsers.sortedWith(compareBy({ it.nickname }, { it.nickname }))
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
            notifyMoeDatabase.userDao().insertUsers(cachedUsers)

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname("")
            assertThat(retrievedUsers).isEqualTo(
                cachedUsers.sortedWith(compareBy({ it.nickname }, { it.nickname }))
            )
        }
    }

    @Test
    fun `Given saved users in db without similar nickname When searching with similar name Then return empty list`() {
        runBlocking {
            val cachedUsers = UserFactory.makeCachedUserList(4).toMutableList()
            notifyMoeDatabase.userDao().insertUsers(cachedUsers)

            val retrievedUsers = notifyMoeDatabase.userDao().searchByNickname("qzdqzd")
            assertThat(retrievedUsers).isNotEqualTo(
                cachedUsers.sortedWith(compareBy({ it.nickname }, { it.nickname }))
            )
            assertThat(retrievedUsers).isEmpty()
        }
    }
    // </editor-fold>
}