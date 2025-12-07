package mc.pesiik.repodetailsimpl.view

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class RepoDetailStateMapperTest {

    private val mapper = RepoDetailStateMapper()

    @Test
    fun `WHEN map repo to ui state THEN return correct ui state`() {
        // Given
        val repo = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
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
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
            isLoading = false
        )

        assertEquals(expectedState, uiState)
    }

    @Test
    fun `WHEN map repo with null description THEN return ui state with null description`() {
        // Given
        val repo = RepoDetails(
            name = "RepoWithoutDescription",
            description = null,
            starsCount = 200,
            forksCount = 100,
            subscribersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z",
        )

        // When
        val uiState = mapper.mapToUiState(repo)

        // Then
        assertEquals("RepoWithoutDescription", uiState.name)
        assertEquals(null, uiState.description)
        assertEquals(100, uiState.forksCount)
        assertEquals(200, uiState.starsCount)
        assertEquals(150, uiState.subscribersCount)
        assertFalse(uiState.isLoading)
    }
}