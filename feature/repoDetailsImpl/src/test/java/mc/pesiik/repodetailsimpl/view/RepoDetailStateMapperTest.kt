package mc.pesiik.repodetailsimpl.view

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

internal class RepoDetailStateMapperTest {

    private val mapper = RepoDetailStateMapper()

    @Test
    fun `WHEN map repo to ui state THEN return correct ui state`() {
        // Given
        val details = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
        )

        // When
        val uiState = mapper.mapToUiState(
            details = details,
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png"
        )

        // Then
        val expectedState = RepoDetailUiState(
            name = "TestRepo",
            description = "Test description",
            forksCount = 50,
            starsCount = 100,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            isLoading = false
        )

        assertEquals(expectedState, uiState)
    }

    @Test
    fun `WHEN map repo with null description THEN return ui state with null description`() {
        // Given
        val details = RepoDetails(
            name = "RepoWithoutDescription",
            description = null,
            starsCount = 200,
            forksCount = 100,
            subscribersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z",
        )
        val expectedState = RepoDetailUiState(
            name = "RepoWithoutDescription",
            description = null,
            forksCount = 100,
            starsCount = 200,
            subscribersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            isLoading = false
        )

        // When
        val uiState = mapper.mapToUiState(
            details = details,
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png"
        )

        // Then
        assertEquals(expectedState, uiState)
    }
}