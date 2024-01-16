package webapp

import database.Post
import org.http4k.template.ViewModel

data class ViewPosts(val posts: List<Post>) : ViewModel {
  override fun template() = "views/ViewPosts.ftl"
}
