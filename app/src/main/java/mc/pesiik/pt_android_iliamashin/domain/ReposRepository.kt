package mc.pesiik.pt_android_iliamashin.domain

interface ReposRepository {
    suspend fun searchRepos(query: String): List<Repo>
}