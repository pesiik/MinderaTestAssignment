package mc.pesiik.reposlistimpl.data

import mc.pesiik.cache.TemporaryCache
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.ReposRepository
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    private val gitReposService: GitReposService,
    private val reposMapper: ReposMapper,
    private val temporaryCache: TemporaryCache,
) : ReposRepository {

    override suspend fun searchRepos(query: String, perPage: Int, page: Int, sort: String?): List<Repo> {
        val reposDtos = gitReposService.searchRepos(query, perPage, page, sort)
        return reposDtos.items.map { repoDto ->
            reposMapper.mapDtoToDomain(repoDto).also { repo ->
                temporaryCache.put(repo.id, repo)
            }
        }
    }
}