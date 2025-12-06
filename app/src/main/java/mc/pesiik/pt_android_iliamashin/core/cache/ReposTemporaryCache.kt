package mc.pesiik.pt_android_iliamashin.core.cache

import mc.pesiik.pt_android_iliamashin.domain.Repo
import javax.inject.Inject

class ReposTemporaryCache @Inject constructor() {

    private val cache = mutableMapOf<Int, Repo>()

    fun putRepo(repo: Repo) {
        cache[repo.id] = repo
    }

    fun getRepoById(repoId: Int): Repo? {
        return cache[repoId]
    }
}