
import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import sun.misc.BASE64Decoder

import scala.io.StdIn


object SimpleServer {

  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route:Route = cors() {
      path("get_custom") {
        post {
          entity(as[akka.http.scaladsl.model.FormData]) { formData:akka.http.scaladsl.model.FormData =>

            val imageByte = (new BASE64Decoder()).decodeBuffer(formData.fields.toString());
            val bytes = new ByteArrayInputStream(imageByte)
            val image = ImageIO.read(bytes)
            ImageIO.write(image, "png", new File("image.png"))
            val rep = IPApp.testImage("image.png")
            complete(rep)
          }
        }
      }
    }


    val bindingFuture = Http().bindAndHandle(route, "127.0.0.1", 8085)

    println(s"Server online at http://127.0.0.1:8085/\nPress RETURN to stop...")
    StdIn.readLine() // Run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

