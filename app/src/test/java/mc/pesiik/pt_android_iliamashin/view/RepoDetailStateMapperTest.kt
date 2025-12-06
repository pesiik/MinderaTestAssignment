package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoDetailStateMapperTest {

    private val mapper = RepoDetailStateMapper()

    @Test
    fun `WHEN map repo to ui state THEN return correct ui state`() {
        // Given
        val repo = Repo(
            id = 1,
            name = "TestRepo",
            ownerLogin = "testOwner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "Test description",
            starCount = 100,
            language = "Kotlin",
            forkCount = 50,
            watcherCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )

        // When
        val uiState = mapper.mapToUiState(repo)

        // Then
        val expectedState = RepoDetailUiState(
            name = "TestRepo",
            description = "Test description",
            forksCount = 50,
            starsCount = 100,
            watchersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )

        assertEquals(expectedState, uiState)
    }

    @Test
    fun `WHEN map repo with null description THEN return ui state with null description`() {
        // Given
        val repo = Repo(
            id = 2,
            name = "RepoWithoutDescription",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = null,
            starCount = 200,
            language = "Java",
            forkCount = 100,
            watcherCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z"
        )

        // When
        val uiState = mapper.mapToUiState(repo)

        // Then
        assertEquals("RepoWithoutDescription", uiState.name)
        assertEquals(null, uiState.description)
        assertEquals(100, uiState.forksCount)
        assertEquals(200, uiState.starsCount)
        assertEquals(150, uiState.watchersCount)
    }
}