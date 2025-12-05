package mc.pesiik.pt_android_iliamashin.view

import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposListStateMapperTest {

    private val mapper = ReposListStateMapper()

    @Test
    fun `WHEN map success result with loading state THEN get success state`() {
        // Given
        val repo = Repo(
            name = "TestRepo",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "Test description"
        )
        val domainResult = Result.success(listOf(repo))
        val previousState = ReposListState.Loading

        // When
        val newState = mapper.mapDomainToUIState(domainResult, previousState)

        // Then
        val expectedUiModel = RepoUiModel(
            name = "TestRepo",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "Test description"
        )
        val expectedState = ReposListState.Success(listOf(expectedUiModel))

        assertEquals(expectedState, newState)
    }

    @Test
    fun `WHEN map error result with success state THEN get error state`() {
        // Given
        val exception = RuntimeException("Network error")
        val domainResult = Result.failure<List<Repo>>(exception)
        val previousState = ReposListState.Success(
            listOf(
                RepoUiModel("Repo1", "url1", "desc1")
            )
        )

        // When
        val newState = mapper.mapDomainToUIState(domainResult, previousState)

        // Then
        assert(newState is ReposListState.Error)
        assertEquals("Network error", (newState as ReposListState.Error).message)
    }

    @Test
    fun `WHEN map success result with error state THEN get success state`() {
        // Given
        val repo = Repo(
            name = "NewRepo",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description"
        )
        val domainResult = Result.success(listOf(repo))
        val previousState = ReposListState.Error("Previous error")

        // When
        val newState = mapper.mapDomainToUIState(domainResult, previousState)

        // Then
        val expectedUiModel = RepoUiModel(
            name = "NewRepo",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description"
        )
        val expectedState = ReposListState.Success(listOf(expectedUiModel))

        assertEquals(expectedState, newState)
    }

    @Test
    fun `WHEN map success result with success state THEN get new success state`() {
        // Given
        val repo = Repo(
            name = "UpdatedRepo",
            ownerAvatarUrl = "http://example.com/updated.png",
            description = "Updated description"
        )
        val domainResult = Result.success(listOf(repo))
        val previousState = ReposListState.Success(
            listOf(
                RepoUiModel("OldRepo", "oldUrl", "oldDesc")
            )
        )

        // When
        val newState = mapper.mapDomainToUIState(domainResult, previousState)

        // Then
        val expectedUiModel = RepoUiModel(
            name = "UpdatedRepo",
            ownerAvatarUrl = "http://example.com/updated.png",
            description = "Updated description"
        )
        val expectedState = ReposListState.Success(listOf(expectedUiModel))

        assertEquals(expectedState, newState)
    }
}