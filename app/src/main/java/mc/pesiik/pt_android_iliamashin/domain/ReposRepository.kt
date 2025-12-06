package mc.pesiik.pt_android_iliamashin.domain

interface ReposRepository {
    suspend fun searchRepos(query: String, perPage: Int, page: Int): List<Repo>
}