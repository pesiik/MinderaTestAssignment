package mc.pesiik.repodetailsimpl.view

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepoDetailsViewModelTest {

    private val repoDetailsRepository: RepoDetailsRepository = mockk()
    private val mapper: RepoDetailStateMapper = mockk()

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN viewModel is initialized THEN load repo details and update state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repoId = 123
        val repo = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
        )
        val expectedState = RepoDetailUiState(
            name = "TestRepo",
            description = "Test description",
            forksCount = 50,
            starsCount = 100,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )

        coEvery { repoDetailsRepository.getRepoDetails(repoId) } returns repo
        every { mapper.mapToUiState(repo) } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsRepository, mapper)
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
        coVerify { repoDetailsRepository.getRepoDetails(repoId) }
        verify { mapper.mapToUiState(repo) }
    }

    @Test
    fun `WHEN repo has null description THEN state contains null description`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repoId = 456
        val repo = RepoDetails(
            name = "RepoWithoutDescription",
            description = null,
            starsCount = 200,
            forksCount = 100,
            subscribersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
        )
        val expectedState = RepoDetailUiState(
            name = "RepoWithoutDescription",
            description = null,
            forksCount = 100,
            starsCount = 200,
            subscribersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z"
        )

        coEvery { repoDetailsRepository.getRepoDetails(repoId) } returns repo
        every { mapper.mapToUiState(repo) } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsRepository, mapper)
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
        assertEquals(null, viewModel.state.value.description)
    }
}
