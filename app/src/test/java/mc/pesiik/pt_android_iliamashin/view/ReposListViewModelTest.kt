package mc.pesiik.pt_android_iliamashin.view

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
        val uiModel = RepoUiModel("name", "avatar", "desc")
        val expectedState = ReposListState.Success(listOf(uiModel))

        coEvery { repository.searchRepos("org") } returns listOf(repo)
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = ReposListState.Loading,
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceUntilIdle()

        assertEquals(expectedState, vm.state.value)
        coVerify { repository.searchRepos("org") }
    }

    @Test
    fun `search repos error updates state to Error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val error = RuntimeException("network")
        val expectedState = ReposListState.Error("mapped error")

        coEvery { repository.searchRepos("org") } throws error
        every {
            mapper.mapDomainToUIState(
                domain = Result.failure(error),
                previousState = ReposListState.Loading,
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceUntilIdle()

        assertEquals(expectedState, vm.state.value)
        coVerify { repository.searchRepos("org") }
    }
}