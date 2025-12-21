package mc.pesiik.reposlistimpl.view

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
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.ReposRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@OptIn(ExperimentalCoroutinesApi::class)
internal class ReposListViewModelTest {

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
        val shimmerModels = List(5) { RepoUiModel(isShimmer = true) }
        val expectedState = ReposListState(repos = listOf(uiModel), searchQuery = "org")

        coEvery {
            repository.searchRepos(
                query = "org",
                perPage = 30,
                page = 1,
            )
        } returns listOf(repo)
        every {
            mapper.shimmerRepoModels(
                count = 5,
                previousState = ReposListState(searchQuery = "org"),
                isPaginating = false,
            )
        } returns shimmerModels
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = any(),
                isPaginating = false,
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceTimeBy(501L)

        assertEquals(expectedState, vm.state.value)
    }

    @Test
    fun `search repos error updates state to Error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val error = RuntimeException("network")
        val shimmerModels = List(5) { RepoUiModel(isShimmer = true) }
        val expectedState = ReposListState(errorMessage = "mapped error", searchQuery = "org")

        coEvery {
            repository.searchRepos(
                query = "org",
                perPage = 30,
                page = 1,
            )
        } throws error
        every {
            mapper.shimmerRepoModels(
                count = 5,
                previousState = ReposListState(searchQuery = "org"),
                isPaginating = false,
            )
        } returns shimmerModels
        every {
            mapper.mapDomainToUIState(
                domain = Result.failure(error),
                previousState = any(),
                isPaginating = false,
            )
        } returns expectedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("org"))
        advanceTimeBy(501L)

        assertEquals(expectedState, vm.state.value)
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

    @Test
    fun `scroll repos list triggers pagination when reaching threshold`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo1: Repo = mockk()
        val repo2: Repo = mockk()
        val initialUiModel = RepoUiModel(
            id = 1,
            name = "repo1",
            ownerLogin = "owner1",
            ownerAvatarUrl = "avatar1",
            description = "desc1",
            starCount = 10,
            language = "Kotlin",
            isShimmer = false,
        )
        val paginatedUiModel = RepoUiModel(
            id = 2,
            name = "repo2",
            ownerLogin = "owner2",
            ownerAvatarUrl = "avatar2",
            description = "desc2",
            starCount = 20,
            language = "Java",
        )
        val shimmerModels = List(5) { RepoUiModel(isShimmer = true) }
        val initialState = ReposListState(
            repos = listOf(initialUiModel),
            searchQuery = "test"
        )
        val paginatedState = ReposListState(
            repos = listOf(initialUiModel, paginatedUiModel),
            searchQuery = "test"
        )

        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
            )
        } returns listOf(repo1)
        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 2,
            )
        } returns listOf(repo2)
        every {
            mapper.shimmerRepoModels(
                count = 5,
                previousState = any(),
                isPaginating = any(),
            )
        } returns shimmerModels
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo1)),
                previousState = any(),
                isPaginating = false,
            )
        } returns initialState
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo2)),
                previousState = any(),
                isPaginating = true,
            )
        } returns paginatedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("test"))
        advanceTimeBy(501L)

        vm.onEvent(ReposListScreenEvent.ScrollReposList(15))
        advanceUntilIdle()

        assertEquals(paginatedState, vm.state.value)
    }

    @Test
    fun `scroll repos list does not trigger pagination below threshold`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo: Repo = mockk()
        val initialState = ReposListState(
            repos = listOf(
                RepoUiModel(
                    id = 1,
                    name = "repo",
                    ownerLogin = "owner",
                    ownerAvatarUrl = "avatar",
                    description = "desc",
                    starCount = 10,
                    language = "Kotlin",
                    isShimmer = false,
                )
            ),
            searchQuery = "test"
        )

        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
            )
        } returns listOf(repo)
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = ReposListState(searchQuery = "test"),
                isPaginating = false,
            )
        } returns initialState
        every {
            mapper.shimmerRepoModels(
                count = 5,
                previousState = any(),
                isPaginating = any(),
            )
        } returns emptyList()

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("test"))
        advanceTimeBy(501L)

        // Scroll to position 10 (below START_PAGINATION_STEP threshold of 15)
        vm.onEvent(ReposListScreenEvent.ScrollReposList(10))
        advanceUntilIdle()

        assertEquals(initialState, vm.state.value)
        coVerify(exactly = 0) {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 2,
            )
        }
    }

    @ParameterizedTest
    @EnumSource(value = ReposSortOption::class, names = ["STARS", "FORKS", "UPDATED"])
    fun `sort by option triggers search with sort parameter`(
        sortOption: ReposSortOption
    ) = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo: Repo = mockk()
        val uiModel = RepoUiModel(
            id = 1,
            name = "repo",
            ownerLogin = "owner",
            ownerAvatarUrl = "avatar",
            description = "desc",
            starCount = 100,
            language = "Kotlin",
        )
        val searchState = ReposListState(
            repos = listOf(uiModel),
            searchQuery = "test"
        )
        val sortedState = ReposListState(
            repos = listOf(uiModel),
            searchQuery = "test",
            selectedSortOption = sortOption,
            isSortMenuOpened = false
        )

        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
                sort = null
            )
        } returns listOf(repo)
        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
                sort = sortOption.value
            )
        } returns listOf(repo)
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = ReposListState(searchQuery = "test"),
                isPaginating = false,
            )
        } returns searchState
        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = searchState.copy(selectedSortOption = ReposSortOption.STARS),
                isPaginating = false,
            )
        } returns sortedState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("test"))
        advanceTimeBy(501L)

        vm.onEvent(ReposListScreenEvent.SortedByOptionSelected(ReposSortOption.STARS))
        advanceUntilIdle()

        assertEquals(sortedState, vm.state.value)
        assertEquals(ReposSortOption.STARS, vm.state.value.selectedSortOption)
        assertEquals(false, vm.state.value.isSortMenuOpened)
        coVerify {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
                sort = sortOption.value
            )
        }
    }

    @Test
    fun `retry load repos triggers search with current query`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)

        val repo: Repo = mockk()
        val uiModel = RepoUiModel(
            id = 1,
            name = "repo",
            ownerLogin = "owner",
            ownerAvatarUrl = "avatar",
            description = "desc",
            starCount = 50,
            language = "Kotlin",
        )
        val shimmerModels = List(5) { RepoUiModel(isShimmer = true) }
        val errorState = ReposListState(
            errorMessage = "network error",
            searchQuery = "test"
        )
        val successState = ReposListState(
            repos = listOf(uiModel),
            searchQuery = "test"
        )

        val error = RuntimeException("network")
        coEvery {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
            )
        } throws error andThen listOf(repo)

        every {
            mapper.shimmerRepoModels(
                count = 5,
                previousState = any(),
                isPaginating = false,
            )
        } returns shimmerModels

        every {
            mapper.mapDomainToUIState(
                domain = Result.failure(error),
                previousState = any(),
                isPaginating = false,
            )
        } returns errorState

        every {
            mapper.mapDomainToUIState(
                domain = Result.success(listOf(repo)),
                previousState = any(),
                isPaginating = false,
            )
        } returns successState

        val vm = ReposListViewModel(repository, mapper)
        vm.onEvent(ReposListScreenEvent.SearchRepos("test"))
        advanceTimeBy(501L)

        assertEquals(errorState, vm.state.value)

        vm.onEvent(ReposListScreenEvent.RetryLoadRepos)
        advanceUntilIdle()

        assertEquals(successState, vm.state.value)
        coVerify(exactly = 2) {
            repository.searchRepos(
                query = "test",
                perPage = 30,
                page = 1,
            )
        }
    }
}