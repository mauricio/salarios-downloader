package org.opendataday.jp

import org.apache.http.client.fluent._
import java.io.{FileOutputStream, File}
import org.slf4j.LoggerFactory

/**
 * User: mauricio
 * Date: 2/24/13
 * Time: 2:57 PM
 */

object DownloadTask {
  val log = LoggerFactory.getLogger(classOf[DownloadTask])
}

class DownloadTask( page : Int ) extends Runnable {

  import DownloadTask.log

  def run() {

    try {

      if ( (page % 100) == 0 ) {
        log.info("Starting page {}", page)
      }

      val destination = new File( "target/pages/%d.html".format(page) )

      if ( !destination.exists() ) {
        val source = "http://www.portaltransparencia.gov.br/servidores/Servidor-ListaServidores.asp?Ordem=2&paramDesc=1&Pagina=" + page

        val data = Request.Get(source).execute().returnContent().asBytes()

        val outputStream = new FileOutputStream( destination )
        outputStream.write(data)
        outputStream.flush()
      }

      if ( (page % 100) == 0 ) {
        log.info("Finishing page {}", page)
      }

    } catch {
      case e : Exception => {
        log.error( "Failed to download file %s".format(page), e )
      }
    }

  }

}