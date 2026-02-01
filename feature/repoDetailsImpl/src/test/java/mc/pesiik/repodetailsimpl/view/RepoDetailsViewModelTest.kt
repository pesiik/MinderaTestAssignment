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
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.repodetailsimpl.interactor.RepoDetailsInteractor
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class RepoDetailsViewModelTest {

    private val repoDetailsInteractor: RepoDetailsInteractor = mockk()
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
        val repo = Repo(
            id = repoId,
            name = "TestRepo",
            starsCount = 2,
            forksCount = 5,
            lastUpdated = "2024-01-10T12:00:00Z",
            user = User(
                login = "owner",
                avatarUrl = "http://example.com/avatar.png",
            ),
            description = "Test description",
            language = "Kotlin",
        )
        val details = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
        )
        val expectedState = RepoDetailUiState(
            name = "TestRepo",
            description = "Test description",
            forksCount = 50,
            starsCount = 100,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )

        coEvery { repoDetailsInteractor.getRepoDetails(repoId) } returns Pair(repo, details)
        every {
            mapper.mapToUiState(
                details = details,
                ownerLogin = "owner",
                ownerAvatarUrl = "http://example.com/avatar.png"
            )
        } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsInteractor, mapper)
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `WHEN repo has null description THEN state contains null description`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repoId = 456
        val repo = Repo(
            id = repoId,
            name = "RepoWithoutDescription",
            starsCount = 10,
            forksCount = 5,
            lastUpdated = "2024-02-20T14:45:00Z",
            user = User(
                login = "owner",
                avatarUrl = "http://example.com/avatar.png",
            ),
            description = null,
            language = "Kotlin",
        )
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
            lastUpdated = "2024-02-20T14:45:00Z"
        )

        coEvery { repoDetailsInteractor.getRepoDetails(repoId) } returns Pair(repo, details)
        every {
            mapper.mapToUiState(
                details = details,
                ownerLogin = "owner",
                ownerAvatarUrl = "http://example.com/avatar.png"
            )
        } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsInteractor, mapper)
        advanceUntilIdle()

        assertEquals(expectedState, viewModel.state.value)
    }
}
