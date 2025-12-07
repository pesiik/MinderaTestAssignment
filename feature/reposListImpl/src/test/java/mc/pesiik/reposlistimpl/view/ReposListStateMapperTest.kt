package mc.pesiik.reposlistimpl.view

import mc.pesiik.reposlistapi.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposListStateMapperTest {

    private val mapper = ReposListStateMapper()

    @Test
    fun `WHEN map error result with success state THEN get error state`() {
        // Given
        val exception = RuntimeException("Network error")
        val domainResult = Result.failure<List<Repo>>(exception)
        val previousState = ReposListState(
            repos = listOf(
                RepoUiModel(
                    id = 1,
                    name = "Repo1",
                    ownerLogin = "owner1",
                    ownerAvatarUrl = "http://example.com/avatar1.png",
                    description = "Description 1",
                    starCount = 100,
                    language = "Kotlin",
                )
            )
        )

        // When
        val newState = mapper.mapDomainToUIState(
            domain = domainResult,
            previousState = previousState,
            isPaginating = false
        )

        // Then
        assert(newState.isError)
        assertEquals("Network error", newState.errorMessage)
        assertEquals(previousState.repos, newState.repos)
    }

    @Test
    fun `WHEN map success result with error state THEN get success state`() {
        // Given
        val repo = Repo(
            id = 1,
            name = "NewRepo",
            ownerLogin = "newOwner",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description",
            starsCount = 50,
            language = "Java",
            forksCount = 10,
            lastUpdated = "2024-06-01T12:00:00Z"
        )
        val domainResult = Result.success(listOf(repo))
        val previousState = ReposListState(errorMessage = "Previous error")

        // When
        val newState = mapper.mapDomainToUIState(
            domain = domainResult,
            previousState = previousState,
            isPaginating = false
        )

        // Then
        val expectedUiModel = RepoUiModel(
            id = 1,
            name = "NewRepo",
            ownerLogin = "newOwner",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description",
            starCount = 50,
            language = "Java",
            isShimmer = false
        )
        val expectedState = ReposListState(
            repos = listOf(expectedUiModel),
            errorMessage = null
        )

        assertEquals(expectedState, newState)
    }

    @Test
    fun `WHEN map success result with success state THEN get new success state`() {
        // Given
        val repo = Repo(
            id = 2,
            name = "UpdatedRepo",
            ownerLogin = "updatedOwner",
            ownerAvatarUrl = "http://example.com/updated.png",
            description = "Updated description",
            starsCount = 150,
            language = "Python",
            forksCount = 30,
            lastUpdated = "2024-06-02T15:00:00Z"
        )
        val domainResult = Result.success(listOf(repo))
        val previousState = ReposListState(
            repos = listOf(
                RepoUiModel(
                    id = 2,
                    name = "OldRepo",
                    ownerLogin = "oldOwner",
                    ownerAvatarUrl = "oldUrl",
                    description = "oldDesc",
                    starCount = 75,
                    language = "C++",
                )
            )
        )

        // When
        val newState = mapper.mapDomainToUIState(
            domain = domainResult,
            previousState = previousState,
            isPaginating = false
        )

        // Then
        val expectedUiModel = RepoUiModel(
            id = 2,
            name = "UpdatedRepo",
            ownerLogin = "updatedOwner",
            ownerAvatarUrl = "http://example.com/updated.png",
            description = "Updated description",
            starCount = 150,
            language = "Python",
            isShimmer = false
        )
        val expectedState = ReposListState(
            repos = listOf(expectedUiModel),
            errorMessage = null
        )

        assertEquals(expectedState, newState)
    }

    @Test
    fun `WHEN map success result with isPaginating true THEN append new repos to existing list`() {
        // Given
        val existingRepo = RepoUiModel(
            id = 1,
            name = "ExistingRepo",
            ownerLogin = "existingOwner",
            ownerAvatarUrl = "http://example.com/existing.png",
            description = "Existing description",
            starCount = 100,
            language = "Kotlin",
            isShimmer = false,
        )
        val previousState = ReposListState(repos = listOf(existingRepo))

        val newRepo = Repo(
            id = 2,
            name = "NewRepo",
            ownerLogin = "newOwner",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description",
            starsCount = 50,
            language = "Java",
            forksCount = 10,
            lastUpdated = "2024-06-01T12:00:00Z",
        )
        val domainResult = Result.success(listOf(newRepo))

        // When
        val newState = mapper.mapDomainToUIState(
            domain = domainResult,
            previousState = previousState,
            isPaginating = true
        )

        // Then
        val expectedNewUiModel = RepoUiModel(
            id = 2,
            name = "NewRepo",
            ownerLogin = "newOwner",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description",
            starCount = 50,
            language = "Java",
            isShimmer = false
        )
        val expectedState = ReposListState(
            repos = listOf(existingRepo, expectedNewUiModel),
            errorMessage = null
        )

        assertEquals(expectedState, newState)
        assertEquals(2, newState.repos.size)
    }

    @Test
    fun `WHEN shimmerRepoModels with isPaginating false THEN return shimmer items only`() {
        // Given
        val previousState = ReposListState(
            repos = listOf(
                RepoUiModel(
                    id = 1,
                    name = "ExistingRepo",
                    ownerLogin = "existingOwner",
                    ownerAvatarUrl = "http://example.com/existing.png",
                    description = "Existing description",
                    starCount = 100,
                    language = "Kotlin",
                )
            )
        )

        // When
        val shimmers = mapper.shimmerRepoModels(
            count = 3,
            previousState = previousState,
            isPaginating = false
        )

        // Then
        assertEquals(3, shimmers.size)
        assert(shimmers.all { it.isShimmer })
    }

    @Test
    fun `WHEN shimmerRepoModels with isPaginating true THEN append shimmers to existing repos`() {
        // Given
        val existingRepo = RepoUiModel(
            id = 1,
            name = "ExistingRepo",
            ownerLogin = "existingOwner",
            ownerAvatarUrl = "http://example.com/existing.png",
            description = "Existing description",
            starCount = 100,
            language = "Kotlin",
        )
        val previousState = ReposListState(repos = listOf(existingRepo))

        // When
        val shimmers = mapper.shimmerRepoModels(
            count = 2,
            previousState = previousState,
            isPaginating = true
        )

        // Then
        assertEquals(3, shimmers.size)
        assertEquals(existingRepo, shimmers[0])
        assert(shimmers[1].isShimmer)
        assert(shimmers[2].isShimmer)
    }

    @Test
    fun `WHEN map success with shimmer items in previous state THEN filter out shimmers`() {
        // Given
        val realRepo = RepoUiModel(
            id = 1,
            name = "RealRepo",
            ownerLogin = "realOwner",
            ownerAvatarUrl = "http://example.com/real.png",
            description = "Real description",
            starCount = 100,
            language = "Kotlin",
            isShimmer = false,
        )
        val shimmerRepo = RepoUiModel(isShimmer = true)
        val previousState = ReposListState(repos = listOf(realRepo, shimmerRepo))

        val newRepo = Repo(
            id = 2,
            name = "NewRepo",
            ownerLogin = "newOwner",
            ownerAvatarUrl = "http://example.com/new.png",
            description = "New description",
            starsCount = 50,
            language = "Java",
            forksCount = 10,
            lastUpdated = "2024-06-01T12:00:00Z",
        )
        val domainResult = Result.success(listOf(newRepo))

        // When
        val newState = mapper.mapDomainToUIState(
            domain = domainResult,
            previousState = previousState,
            isPaginating = true
        )

        // Then
        assertEquals(2, newState.repos.size)
        assert(newState.repos.none { it.isShimmer })
        assertEquals(realRepo, newState.repos[0])
    }
}
