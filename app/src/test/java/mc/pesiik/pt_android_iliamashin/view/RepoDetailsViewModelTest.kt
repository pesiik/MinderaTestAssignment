package mc.pesiik.pt_android_iliamashin.view

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.RepoDetailsRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoDetailsViewModelTest {

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
        val repo = Repo(
            id = repoId,
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
        val expectedState = RepoDetailUiState(
            name = "TestRepo",
            description = "Test description",
            forksCount = 50,
            starsCount = 100,
            watchersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )

        every { repoDetailsRepository.getRepoDetails(repoId) } returns repo
        every { mapper.mapToUiState(repo) } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsRepository, mapper)

        assertEquals(expectedState, viewModel.state.value)
        verify { repoDetailsRepository.getRepoDetails(repoId) }
        verify { mapper.mapToUiState(repo) }
    }

    @Test
    fun `WHEN repo has null description THEN state contains null description`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repoId = 456
        val repo = Repo(
            id = repoId,
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
        val expectedState = RepoDetailUiState(
            name = "RepoWithoutDescription",
            description = null,
            forksCount = 100,
            starsCount = 200,
            watchersCount = 150,
            lastUpdated = "2024-02-20T14:45:00Z"
        )

        every { repoDetailsRepository.getRepoDetails(repoId) } returns repo
        every { mapper.mapToUiState(repo) } returns expectedState

        val viewModel = RepoDetailsViewModel(repoId, repoDetailsRepository, mapper)

        assertEquals(expectedState, viewModel.state.value)
        assertEquals(null, viewModel.state.value.description)
    }
}
