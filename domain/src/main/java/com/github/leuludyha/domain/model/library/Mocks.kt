package com.github.leuludyha.domain.model.library

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.authentication.NearbyConnection
import com.github.leuludyha.domain.model.library.Mocks.mainUser
import com.github.leuludyha.domain.model.library.Mocks.user2
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import java.util.concurrent.CompletableFuture

/** A mock work we can use to preview stuff or test */
object Mocks {

    private val dumbWorkMrFox = Work(
        title = "Fantastic Mr Fox",
        id = "OL45804W",
        authors = flowOf(listOf()),
        editions = flowOf(listOf()),
        covers = flowOf(
            listOf(
                6498519,
                8904777,
                108274,
                233884,
                1119236,
                10222599,
                10482837,
                3216657,
                10519563,
                10835922,
                10835924,
                10861366,
                10883671,
                8760472,
                12583098,
                10482548,
                10831929,
                10835926,
                12333895,
                12498647,
                7682784,
                12143357,
                12781739,
                3077458
            ).map { Cover(it.toLong()) }),
        subjects = flowOf(
            listOf(
                "Animals",
                "Hunger",
                "Open Library Staff Picks",
                "Juvenile fiction",
                "Children's stories, English",
                "Foxes",
                "Fiction",
                "Zorros",
                "Ficci\u00f3n juvenil",
                "Tunnels",
                "Interviews",
                "Farmers",
                "Children's stories",
                "Rats",
                "Welsh Authors",
                "English Authors",
                "Thieves",
                "Tricksters",
                "Badgers",
                "Children's fiction",
                "Foxes, fiction",
                "Underground",
                "Renards",
                "Romans, nouvelles, etc. pour la jeunesse",
                "Children's literature",
                "Plays",
                "Children's plays",
                "Children's stories, Welsh",
                "Agriculteurs",
                "Large type books",
                "Fantasy fiction"
            )
        ),
    )

    val authorRoaldDahl = Author(
        wikipedia = null,
        name = "Roald Dahl",
        birthDate = "13 September 1916",
        deathDate = "23 November 1990",
        //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
        id = "OL34184A",
        works = flowOf(listOf(dumbWorkMrFox)),
        covers = flowOf(
            listOf(
                9395323,
                9395316,
                9395314,
                9395313,
                6287214
            ).map { Cover(it.toLong()) }),
    )

    val editionMrFox = Edition(
        title = "Fantastic Mr. Fox",
        id = "OL44247403M",
        isbn10 = null,
        isbn13 = "9780142418222",
        authors = flowOf(listOf(authorRoaldDahl)),
        works = flowOf(listOf(dumbWorkMrFox)),
        covers = flowOf(listOf(Cover(13269612))),
    )

    val workMrFox = Work(
        title = "Fantastic Mr Fox",
        id = "OL45804W",
        authors = flowOf(listOf(authorRoaldDahl)),
        editions = flowOf(listOf(editionMrFox)),
        covers = flowOf(
            listOf(
                6498519,
                8904777,
                108274,
                233884,
                1119236,
                10222599,
                10482837,
                3216657,
                10519563,
                10835922,
                10835924,
                10861366,
                10883671,
                8760472,
                12583098,
                10482548,
                10831929,
                10835926,
                12333895,
                12498647,
                7682784,
                12143357,
                12781739,
                3077458
            ).map { Cover(it.toLong()) }),
        subjects = flowOf(
            listOf(
                "Animals",
                "Hunger",
                "Open Library Staff Picks",
                "Juvenile fiction",
                "Children's stories, English",
                "Foxes",
                "Fiction",
                "Zorros",
                "Ficci\u00f3n juvenil",
                "Tunnels",
                "Interviews",
                "Farmers",
                "Children's stories",
                "Rats",
                "Welsh Authors",
                "English Authors",
                "Thieves",
                "Tricksters",
                "Badgers",
                "Children's fiction",
                "Foxes, fiction",
                "Underground",
                "Renards",
                "Romans, nouvelles, etc. pour la jeunesse",
                "Children's literature",
                "Plays",
                "Children's plays",
                "Children's stories, Welsh",
                "Agriculteurs",
                "Large type books",
                "Fantasy fiction"
            )
        ),
    )

    val workMrFoxPref = WorkPreference(
        work = workMrFox,
        readingState = WorkPreference.ReadingState.READING,
        possessed = true,
        rating = null
    )

    private val dumbWork1984 = Work(
        title = "1984",
        id = "OL1168083W",
        editions = flowOf(listOf()),
        authors = flowOf(listOf()),
        covers = flowOf(listOf(Cover(12725451L))),
        subjects = flowOf(listOf("Censorship", "Futurology", "Surveillance"))
    )

    val authorGeorgeOrwell: Author = Author(
        id = "9160343",
        name = "George Orwell",
        birthDate = null,
        deathDate = null,
        wikipedia = null,
        works = flowOf(listOf(dumbWork1984)),
        covers = flowOf(listOf(Cover(12919044L)))
    )

    val edition1984 = Edition(
        id = "1984Edition",
        title = "1984",
        isbn13 = "1984isbn13",
        isbn10 = "1984isbn10",
        authors = flowOf(listOf(authorGeorgeOrwell)),
        works = flowOf(listOf(dumbWork1984)),
        covers = flowOf(listOf(Cover(12725451L)))
    )

    val work1984: Work = Work(
        title = "1984",
        id = "OL1168083W",
        editions = flowOf(listOf()),
        authors = flowOf(listOf(authorGeorgeOrwell)),
        covers = flowOf(listOf(Cover(12725451L))),
        subjects = flowOf(listOf("Censorship", "Futurology", "Surveillance"))
    )

    val workLaFermeDesAnimaux: Work = Work(
        title = "La Ferme des Animaux",
        id = "OL26038920W",
        editions = flowOf(listOf()),
        authors = flowOf(listOf(authorGeorgeOrwell)),
        covers = flowOf(listOf(Cover(13147152L))),
        subjects = flowOf(listOf("Fiction", "Historical", "Political Science"))
    )

    val emptyWork = Work(
        title = "",
        id = "",
        editions = flowOf(),
        authors = flowOf(),
        covers = flowOf(),
        subjects = flowOf()
    )

    val userPreferences: UserPreferences = UserPreferences()
    val workPreferences: Map<String, WorkPreference> = mapOf(
        work1984.id to WorkPreference(work1984, WorkPreference.ReadingState.READING, false),
        workLaFermeDesAnimaux.id to WorkPreference(
            workLaFermeDesAnimaux,
            WorkPreference.ReadingState.FINISHED,
            true
        )
    )

    val mainUser: MainUser = MainUser(
        UUID.randomUUID().toString(),
        username = "Mockentosh",
        userPreferences = userPreferences,
        workPreferences = flowOf(workPreferences),
        phoneNumber = "",
        profilePictureUrl = "",
        statistics = UserStatistics(
            preferredWorks = listOf(work1984),
            preferredSubjects = listOf("Censorship"),
            preferredAuthors = listOf(authorGeorgeOrwell),
            averageNumberOfPages = 42
        ),
        friends = listOf(),
        latitude = 46.521131,
        longitude = 6.566055
    )

    val user2: MainUser = MainUser(
        UUID.randomUUID().toString(),
        username = "MockMike",
        userPreferences = userPreferences,
        workPreferences = flowOf(workPreferences),
        phoneNumber = "",
        profilePictureUrl = "",
        statistics = UserStatistics(
            preferredWorks = listOf(workLaFermeDesAnimaux),
            preferredSubjects = listOf("Libertarianism"),
            preferredAuthors = listOf(authorGeorgeOrwell),
            averageNumberOfPages = 42
        ),
        friends = listOf(mainUser),
        latitude = 46.522397,
        longitude = 6.563162
    )

    val authContext: AuthenticationContext = AuthenticationContext(mainUser, NearbyConnection.Empty)

    //These are a few locations near EPFL, Pair<Latitude, Longitude>
    val userList: List<User> = listOf(
        mainUser,
        user2
    )

    val libraryRepository = MockLibraryRepositoryImpl()

    fun bitmap(): Bitmap = Bitmap.createBitmap(
        intArrayOf(0x000000, 0xFFFFFF, 0x000000, 0xFFFFFF, 0x000000, 0xFFFFFF),
        3, 2, Bitmap.Config.ALPHA_8)
}

class MockLibraryRepositoryImpl : LibraryRepository {
    private val savedWorks: HashMap<String, Work> = hashMapOf()
    private val savedEditions: HashMap<String, Edition> = hashMapOf()
    private val savedAuthors: HashMap<String, Author> = hashMapOf()
    private val savedWorkPrefs: HashMap<String, WorkPreference> = hashMapOf()

    private val worksFlow = MutableStateFlow(listOf<Work>())
    private val editionsFlow = MutableStateFlow(listOf<Edition>())
    private val authorsFlow = MutableStateFlow(listOf<Author>())
    private val workPrefsFlow = MutableStateFlow(listOf<WorkPreference>())

    override fun searchRemotely(query: String): Flow<PagingData<Work>> {
        return flowOf(PagingData.from(listOf(Mocks.workMrFox)))
        // TODO("How to test PagingData???")
    }

    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        if (workId == Mocks.workMrFox.id)
            flowOf(Result.Success(Mocks.workMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        if (editionId == Mocks.editionMrFox.id)
            flowOf(Result.Success(Mocks.editionMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        if (isbn == Mocks.editionMrFox.isbn10 || isbn == Mocks.editionMrFox.isbn13)
            flowOf(Result.Success(Mocks.editionMrFox))
        else
            flowOf(Result.Error("isbn not found"))

    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        if (authorId == Mocks.authorRoaldDahl.id)
            flowOf(Result.Success(Mocks.authorRoaldDahl))
        else
            flowOf(Result.Error("id not found"))

    override suspend fun saveLocally(work: Work) {
        savedWorks[work.id] = work
        worksFlow.value = savedWorks.values.toList()
    }

    override suspend fun saveLocally(author: Author) {
        savedAuthors[author.id] = author
        authorsFlow.value = savedAuthors.values.toList()
    }

    override suspend fun saveLocally(edition: Edition) {
        savedEditions[edition.id] = edition
        editionsFlow.value = savedEditions.values.toList()
    }

    override suspend fun saveLocally(workPref: WorkPreference) {
        savedWorkPrefs[workPref.work.id] = workPref
        workPrefsFlow.value = savedWorkPrefs.values.toList()
    }

    override suspend fun deleteLocally(work: Work) {
        savedWorks.remove(work.id)
        worksFlow.value = savedWorks.values.toList()
    }

    override suspend fun deleteLocally(author: Author) {
        savedAuthors.remove(author.id)
        authorsFlow.value = savedAuthors.values.toList()
    }

    override suspend fun deleteLocally(edition: Edition) {
        savedEditions.remove(edition.id)
        editionsFlow.value = savedEditions.values.toList()
    }

    override suspend fun deleteLocally(workPref: WorkPreference) {
        savedWorkPrefs.remove(workPref.work.id)
        workPrefsFlow.value = savedWorkPrefs.values.toList()
    }

    override fun getWorkLocally(workId: String): Flow<Work> =
        if (savedWorks[workId] != null)
            flowOf(savedWorks[workId]!!)
        else
            flowOf()

    override fun getAuthorLocally(authorId: String): Flow<Author> =
        if (savedAuthors[authorId] != null)
            flowOf(savedAuthors[authorId]!!)
        else
            flowOf()

    override fun getEditionLocally(editionId: String): Flow<Edition> =
        if (savedEditions[editionId] != null)
            flowOf(savedEditions[editionId]!!)
        else
            flowOf()

    override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
        val editions = savedEditions.values.filter { it.isbn10 == isbn || it.isbn13 == isbn }
        return if (editions.firstOrNull() == null)
            flowOf()
        else
            flowOf(editions.first())
    }

    override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> =
        if (savedWorkPrefs[workId] != null)
            flowOf(savedWorkPrefs[workId]!!)
        else
            flowOf()

    override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> =
        workPrefsFlow

    override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> {
        return flowOf(Mocks.bitmap())
    }
}

class MockUserRepositoryImpl : UserRepository {
    /** Returns a completed future containing [Mocks.mainUser] */
    override fun getUserFromPhoneNumber(phoneNumber: String): CompletableFuture<User> =
        CompletableFuture.completedFuture(mainUser)

    /** Returns a completed future containing [Mocks.mainUser] and [Mocks.user2] */
    override fun getNearbyUsers(
        latitudeMax: Double,
        longitudeMax: Double,
        latitudeMin: Double,
        longitudeMin: Double
    ): CompletableFuture<List<User>> =
        CompletableFuture.completedFuture(listOf(mainUser, user2))

    /** Returns a list containing [Mocks.mainUser] and [Mocks.user2] */
    override fun getNeighbouringUsersOf(
        user: User,
        distance: (User, User) -> Float,
        n: Int
    ): List<User> =
        listOf(user2)

}
