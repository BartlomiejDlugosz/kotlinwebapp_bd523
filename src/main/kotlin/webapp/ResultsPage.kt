package webapp

import org.http4k.template.ViewModel

data class ResultsPage(val results: List<WordScore>) : ViewModel {
  override fun template() = "views/ResultsPage.ftl"
}
