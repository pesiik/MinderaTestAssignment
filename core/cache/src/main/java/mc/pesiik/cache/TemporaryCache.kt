package mc.pesiik.cache

import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TemporaryCache @Inject constructor() {
    private val storage: MutableMap<Int, Any> = mutableMapOf()

    fun put(id: Int, value: Any) {
        storage[id] = value
    }

    fun <T> getById(id: Int): T? {
        return storage[id] as? T
    }
}