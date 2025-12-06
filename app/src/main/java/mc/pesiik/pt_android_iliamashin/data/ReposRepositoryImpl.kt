package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.core.navigation.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.ReposRepository
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val gitReposService: GitReposService,
    private val reposMapper: ReposMapper,
    private val reposTemporaryCache: ReposTemporaryCache,
) : ReposRepository {

    override suspend fun searchRepos(query: String, perPage: Int, page: Int): List<Repo> {
        val reposDtos = gitReposService.searchRepos(query, perPage, page)
        return reposDtos.items.map { repoDto ->
            reposMapper.mapDtoToDomain(repoDto).also(reposTemporaryCache::putRepo)
        }
    }
}