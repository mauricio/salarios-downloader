package org.opendataday.jp

import java.util.concurrent.Executors
import org.slf4j.LoggerFactory
import java.io.File

/**
 * User: mauricio
 * Date: 2/24/13
 * Time: 2:53 PM
 */
object Main {

  val log = LoggerFactory.getLogger(classOf[Main])

  def main( args : Array[String] ) {

    val pool = Executors.newFixedThreadPool(10)

    val destination = new File("target/pages")

    if ( !destination.exists() ) {
      destination.mkdirs()
    }

    var count = 1

    while ( count <= 65115 ) {
      pool.submit( new DownloadTask(count) )
      count += 1
    }

    pool.shutdown()

    while ( pool.isTerminated ) {
      log.info("Sleeping while waiting for termination")
      Thread.sleep(5000)
    }

  }

}

class Main