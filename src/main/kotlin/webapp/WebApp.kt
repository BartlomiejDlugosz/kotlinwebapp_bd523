package webapp

import database.Post
import database.PostsDatabase
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import org.http4k.routing.*
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.FreemarkerTemplates
import java.net.URLDecoder

val letterScoring: Map<Char, Int> = mapOf(
  'A' to 1,
  'B' to 3,
  'C' to 3,
  'D' to 2,
  'E' to 1,
  'F' to 4,
  'G' to 2,
  'H' to 4,
  'I' to 1,
  'J' to 8,
  'K' to 5,
  'L' to 1,
  'M' to 3,
  'N' to 1,
  'O' to 1,
  'P' to 3,
  'Q' to 10,
  'R' to 1,
  'S' to 1,
  'T' to 1,
  'U' to 1,
  'V' to 4,
  'W' to 4,
  'X' to 8,
  'Y' to 4,
  'Z' to 10,
  ' ' to 0
)

data class WordScore(val word: String, val score: Int, val charScores: List<Pair<Char, Int>>)

val app: HttpHandler = routes(

  "/index" bind GET to {
    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = IndexPage(listOf("Bartek", "Stefan", "Ishan"))
    Response(OK).body(renderer(viewModel))
  },

  "/submit" bind POST to { request ->
    val body = request.bodyString()
    val decodedWords = URLDecoder.decode(body, "UTF-8")
    val wordValues: List<String> = decodedWords.split("&").map { it.substring(8).substringAfter("=") }

    val scores: List<WordScore> = wordValues.map { word ->
      val charMappings: List<Pair<Char, Int>> =
        word.map { it.uppercaseChar() to letterScoring.getOrDefault(it.uppercaseChar(), 0) }
      WordScore(word, charMappings.sumOf { it.second }, charMappings)
    }
    println(scores)

    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = ResultsPage(scores.sortedByDescending { it.score })
    Response(OK).body(renderer(viewModel))
  },

  "posts/" bind GET to {
    val postsDatabase = PostsDatabase()

    val posts = postsDatabase.loadAllPosts()

    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = ViewPosts(posts)
    Response(OK).body(renderer(viewModel))
  },

  "posts/add" bind POST to { request ->
    val title = request.form("title")!!
    val body = request.form("body")!!

    val postsDatabase = PostsDatabase()

    postsDatabase.addPost(Post(title = title, body = body))

    Response(FOUND).header("Location", "/posts/")
  },

  "posts/delete/{id}" bind POST to { request ->
    val id = request.path("id")
    if (id != null) {
      val postsDatabase = PostsDatabase()

      postsDatabase.removePost(id)
    }
    Response(FOUND).header("Location", "/posts/")
  },

  "/assets" bind static(ResourceLoader.Directory("src/main/resources/assets")),
)


fun main() {
  val server = app.asServer(Jetty(9000)).start()
  println("Server started on http://localhost:" + server.port())

}
