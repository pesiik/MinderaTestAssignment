package mc.pesiik.pt_android_iliamashin.domain

interface RepoDetailsRepository {
    suspend fun getRepoDetails(repoId: Int): RepoDetails
}