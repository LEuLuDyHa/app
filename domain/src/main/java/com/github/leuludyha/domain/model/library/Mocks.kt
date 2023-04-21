package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.flowOf
import java.util.*

/** A mock work we can use to preview stuff or test */
object Mocks {

    private val dumbWorkMrFox = Work(
        title = "Fantastic Mr Fox",
        id = "OL45804W",
        authors = flowOf(listOf()),
        editions = flowOf(listOf()),
        covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{ Cover(it.toLong()) }),
        subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
    )

    val authorRoaldDahl = Author(
        wikipedia = null,
        name = "Roald Dahl",
        birthDate = "13 September 1916",
        deathDate = "23 November 1990",
        //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
        id = "OL34184A",
        works = flowOf(listOf(dumbWorkMrFox)),
        covers = flowOf ( listOf(9395323, 9395316, 9395314, 9395313, 6287214).map { Cover(it.toLong()) } ),
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
        covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{Cover(it.toLong())}),
        subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
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

    val userPreferences: UserPreferences = UserPreferences(
        mutableMapOf(
            Pair(work1984.id, WorkPreference(work1984, WorkPreference.ReadingState.READING, false)),
            Pair(
                workLaFermeDesAnimaux.id,
                WorkPreference(workLaFermeDesAnimaux, WorkPreference.ReadingState.FINISHED, true)
            )
        )
    )

    val mainUser: MainUser = MainUser(
        UUID.randomUUID().toString(),
        username = "Mockentosh",
        preferences = userPreferences,
        phoneNumber = "",
        profilePictureUrl = "",
        statistics = UserStatistics(
            preferredWorks = listOf(work1984),
            preferredSubjects = listOf("Censorship"),
            preferredAuthors = listOf(authorGeorgeOrwell),
            averageNumberOfPages = 42
        ),
        friends = listOf(),
        latitude = 0.0,
        longitude = 0.0
    )

    val authContext: AuthenticationContext = AuthenticationContext(mainUser)

    //These are a few locations near EPFL, Pair<Latitude, Longitude>
    val userLocationList: List<Pair<Double, Double>> = listOf(
        Pair(46.521131, 6.566055),
        Pair(46.522397, 6.563162),
        Pair(46.518300, 6.560984)
    )
}