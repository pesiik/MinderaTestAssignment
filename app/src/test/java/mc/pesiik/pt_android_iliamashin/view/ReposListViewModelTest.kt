package mc.pesiik.pt_android_iliamashin.view

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.ReposRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReposListViewModelTest {

    private val repository: ReposRepository = mockk()
    private val mapper: ReposListStateMapper = mockk()

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `search repos success updates state to Success`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo: Repo = mockk()
        val uiModel = RepoUiModel(
            id = 123,
            name = "name",
            ownerLogin = "login",
            ownerAvatarUrl = "avatar",
            description = "desc",
            starCount = 42,
            language = "Kotlin",
        )
        val expectedState = ReposListState(repos = listOf(uiModel))

        coEvery { repository.searchRepos("org") } returns listOf(repo)
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = ReposListState(isIdle = true, searchQuery = "org"),
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceTimeBy(501L)

        assertEquals(expectedState, vm.state.value)
        coVerify { repository.searchRepos("org") }
    }

    @Test
    fun `search repos error updates state to Error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val error = RuntimeException("network")
        val expectedState = ReposListState(errorMessage = "mapped error")

        coEvery { repository.searchRepos("org") } throws error
        every {
            mapper.mapDomainToUIState(
                domain = Result.failure(error),
                previousState = ReposListState(isIdle = true, searchQuery = "org"),
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceTimeBy(501L)

        assertEquals(expectedState, vm.state.value)
        coVerify { repository.searchRepos("org") }
    }

    @Test
    fun `toggle search mode updates isInSearchMode state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val vm = ReposListViewModel(repository, mapper)

        vm.onEvent(ReposListScreenEvent.ToggleSearchMode(true))

        assertEquals(true, vm.state.value.isInSearchMode)

        vm.onEvent(ReposListScreenEvent.ToggleSearchMode(false))
        advanceUntilIdle()

        assertEquals(false, vm.state.value.isInSearchMode)
    }

    @Test
    fun `back button clicked when in search mode toggles search mode off`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.ToggleSearchMode(true))

        vm.onEvent(ReposListScreenEvent.BackButtonClicked)
        advanceUntilIdle()

        assertEquals(false, vm.state.value.isInSearchMode)
        assertEquals(false, vm.state.value.shouldCloseApp)
    }

    @Test
    fun `back button clicked when not in search mode sets shouldCloseApp true`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val vm = ReposListViewModel(repository, mapper)

        vm.onEvent(ReposListScreenEvent.BackButtonClicked)
        advanceUntilIdle()

        assertEquals(false, vm.state.value.isInSearchMode)
        assertEquals(true, vm.state.value.shouldCloseApp)
    }
}