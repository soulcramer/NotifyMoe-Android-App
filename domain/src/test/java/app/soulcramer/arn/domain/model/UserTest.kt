package app.soulcramer.arn.domain.model

import app.soulcramer.arn.domain.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserTest {

    private val validNickName = "ValidNickname"

    @Test
    fun `given nickname starting with lowercase g When checking if it has a valid nickname then return false`() {
        val testUser = UserFactory.makeUser(name = "g$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(false)
    }

    @Test
    fun `given nickname starting with lowercase fb When checking if it has a valid nickname then return false`() {
        val testUser = UserFactory.makeUser(name = "fb$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(false)
    }

    @Test
    fun `given nickname starting with lowercase t When checking if it has a valid nickname then return false`() {
        val testUser = UserFactory.makeUser(name = "t$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(false)
    }

    @Test
    fun `given empty nickname When checking if it has a valid nickname then return false`() {
        val testUser = UserFactory.makeUser(name = "")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(false)
    }

    @Test
    fun `given nickname starting with uppercase G When checking if it has a valid nickname then return true`() {
        val testUser = UserFactory.makeUser(name = "G$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(true)
    }

    @Test
    fun `given nickname starting with uppercase FB When checking if it has a valid nickname then return true`() {
        val testUser = UserFactory.makeUser(name = "FB$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(true)
    }

    @Test
    fun `given nickname starting with uppercase T When checking if it has a valid nickname then return true`() {
        val testUser = UserFactory.makeUser(name = "T$validNickName")

        val hasValidNickName = testUser.hasValidNickName()

        assertThat(hasValidNickName).isEqualTo(true)
    }
}