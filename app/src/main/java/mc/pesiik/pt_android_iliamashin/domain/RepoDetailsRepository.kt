package mc.pesiik.pt_android_iliamashin.domain

interface RepoDetailsRepository {
    fun getRepoDetails(repoId: Int): Repo
}