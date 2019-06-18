package app.soulcramer.arn.domain.interactor

import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.prefs.PreferenceStorage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HasLoggedUserTest {

    private lateinit var hasLoggedUser: HasLoggedUser

    private lateinit var testPreferenceStorage: PreferenceStorage

    @Before
    fun setUp() {
        testPreferenceStorage = mockk()
        hasLoggedUser = HasLoggedUser(testPreferenceStorage)
    }

    @Test
    fun `Given have user logged When checking logged user existence Then return true`() {
        runBlocking {
            every { testPreferenceStorage.currentUserId } returns "qzdqd"
            val result = hasLoggedUser()

            assertThat(result.succeeded).isEqualTo(true)
            val success = result as Success<Boolean>
            assertThat(success.data).isEqualTo(true)
        }
    }

    @Test
    fun `Given have no user logged When checking logged user existence Then return true`() {
        runBlocking {
            coEvery { testPreferenceStorage.currentUserId } returns null
            val result = hasLoggedUser()

            assertThat(result.succeeded).isEqualTo(true)
            val success = result as Success<Boolean>
            assertThat(success.data).isEqualTo(false)
        }
    }
}