package app.soulcramer.arn.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.soulcramer.arn.cache.mapper.UserEntityMapper
import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.cache.test.factory.UserFactory
import app.soulcramer.arn.data.model.UserEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class UserCacheImplTest {

    private var notifyMoeDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        NotifyMoeDatabase::class.java
    ).allowMainThreadQueries().build()

    private var entityMapper = UserEntityMapper()
    private var preferencesHelper = PreferencesHelper(ApplicationProvider.getApplicationContext())

    private val databaseHelper = UserCacheImpl(notifyMoeDatabase, entityMapper, preferencesHelper)

    // <editor-fold desc="Save Users">
    @Test
    fun `Given a user When saving him them Then user should be saved`() {
        runBlocking {
            val userCount = 1
            val userEntity = UserFactory.makeUserEntity()

            databaseHelper.saveUser(userEntity)
            checkNumRowsInUsersTable(userCount)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Get Users">
    @Test
    fun `Given saved users in db When searching with similar name Then return all cached users with the nickname`() {
        runBlocking {
            val nickname = "abcd"
            val userEntities = UserFactory.makeUserListWithCloseNickname(2, nickname)
            val cachedUsers = mutableListOf<CachedUser>()
            userEntities.forEach { userEntity: UserEntity ->
                cachedUsers.add(entityMapper.mapToCached(userEntity))
            }
            insertUsers(cachedUsers)

            val users = databaseHelper.searchUsers(nickname)
            assertThat(users).containsExactlyElementsIn(userEntities)
        }
    }
    // </editor-fold>

    // <editor-fold desc="Get Users">

    @Test
    fun `Given saved users in db When getting all them Then return all cahced users`() {
        runBlocking {
            val userEntities = UserFactory.makeUserEntityList(2)
            val cachedUsers = mutableListOf<CachedUser>()
            userEntities.forEach { userEntity: UserEntity ->
                cachedUsers.add(entityMapper.mapToCached(userEntity))
            }
            insertUsers(cachedUsers)

            val users = databaseHelper.getUsers()
            assertThat(users).containsExactlyElementsIn(userEntities)
        }
    }
    // </editor-fold>

    private suspend fun insertUsers(cachedUsers: List<CachedUser>) {
        cachedUsers.forEach {
            notifyMoeDatabase.userDao().insertUsers(it)
        }
    }

    private suspend fun checkNumRowsInUsersTable(expectedRows: Int) {
        val numberOfRows = notifyMoeDatabase.userDao().allUserCount()
        assertThat(expectedRows).isEqualTo(numberOfRows)
    }
}