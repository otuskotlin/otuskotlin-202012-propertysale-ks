package ru.otus.otuskotlin.propertysale.backend.repository.cassandra

import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.GenericContainer
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat.FlatRepositoryCassandra
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals

class CassandraContainer : GenericContainer<CassandraContainer>("cassandra")

internal class CassandraFlatsTest {

    companion object {
        private val PORT = 9042
        private val keyspace = "test_keyspace"
        private lateinit var container: CassandraContainer
        private lateinit var repo: FlatRepositoryCassandra

        @BeforeClass
        @JvmStatic
        fun tearUp() {
            container = CassandraContainer()
                .withExposedPorts(PORT)
                .withStartupTimeout(Duration.ofSeconds(300L))
                .apply {
                    start()
                }

            repo = FlatRepositoryCassandra(
                keyspaceName = keyspace,
                hosts = container.host,
                port = container.getMappedPort(PORT),
                testing = true,
                initObjects = listOf(
                    BePsFlatModel(
                        id = BePsFlatIdModel("test-id1"),
                        name = "test-flat",
                        actions = mutableSetOf(BePsActionModel(content = "id1"), BePsActionModel(content = "id2")),
                    ),
                    BePsFlatModel(
                        id = BePsFlatIdModel("test-id2"),
                        name = "test-flat1",
                        actions = mutableSetOf(BePsActionModel(content = "id1"), BePsActionModel(content = "id2")),
                    ),
                    BePsFlatModel(
                        id = BePsFlatIdModel("test-id3"),
                        name = "flat-0",
                        actions = mutableSetOf(BePsActionModel(content = "id1"), BePsActionModel(content = "id2")),
                    ),
                    BePsFlatModel(
                        id = BePsFlatIdModel("test-id4"),
                        name = "test-flat2",
                        actions = mutableSetOf(BePsActionModel(content = "id1"), BePsActionModel(content = "id2")),
                    ),
                    BePsFlatModel(
                        id = BePsFlatIdModel("test-id5"),
                        name = "flat-1",
                        actions = mutableSetOf(BePsActionModel(content = "id1"), BePsActionModel(content = "id2")),
                    ),
                )
            ).init()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            container.close()
        }
    }

    @Test
    fun flatReadTest() {
        runBlocking {
            val context = BePsContext(
                requestFlatId = BePsFlatIdModel("test-id4")
            )
            val model = repo.read(context)
            assertEquals(model, context.responseFlat)
            assertEquals("test-flat2", model.name)
        }
    }

    @Test
    fun flatListTest() {
        runBlocking {
            val context = BePsContext(
                flatFilter = BePsFlatFilterModel(
                    text = "test",
                    offset = 1,
                    count = 2,
                )
            )
            val response = repo.list(context)
            assertEquals(response, context.responseFlats)
            assertEquals(2, context.pageCount)
            assertEquals(2, response.size)
        }
    }

    @Test
    fun flatCreateTest() {
        runBlocking {
            val flat = BePsFlatModel(
                name = "created-flat",
                description = "about flat",
            )
            val context = BePsContext(
                requestFlat = flat
            )
            val result = repo.create(context)
            assertEquals(result, context.responseFlat)
            assertEquals("created-flat", result.name)
            assertEquals("about flat", result.description)
            val context2 = BePsContext(requestFlatId = result.id)
            repo.read(context2)
            assertEquals("created-flat", context2.responseFlat.name)
            assertEquals("about flat", context2.responseFlat.description)
        }
    }

    @Test
    fun flatUpdateTest() {
        runBlocking {
            val flat = BePsFlatModel(
                id = BePsFlatIdModel("test-id1"),
                name = "updated-flat",
                description = "about flat",
            )
            val context = BePsContext(
                requestFlat = flat
            )
            val result = repo.update(context)
            assertEquals(result, context.responseFlat)
            assertEquals("updated-flat", result.name)
            assertEquals("about flat", result.description)
            val context2 = BePsContext(requestFlatId = BePsFlatIdModel("test-id1"))
            repo.read(context2)
            assertEquals("updated-flat", context2.responseFlat.name)
            assertEquals("about flat", context2.responseFlat.description)
        }
    }

    @Test
    fun flatDeleteTest() {
        runBlocking {
            val context = BePsContext(
                requestFlatId = BePsFlatIdModel("test-id5")
            )
            val model = repo.delete(context)
            assertEquals(model, context.responseFlat)
            assertEquals("flat-1", model.name)
        }
    }
}
