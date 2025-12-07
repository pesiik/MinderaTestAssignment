package mc.pesiik.reposlistapi.domain

interface ReposRepository {
    suspend fun searchRepos(query: String, perPage: Int, page: Int, sort: String? = null): List<Repo>
}