package zio.features.server.services

import zio._
import java.time.Duration

trait Cache[K, V] {
  def get(k: K): ZIO[Any, Throwable, Option[V]]

  def put(k: K, v: V, timeout: Duration): ZIO[Any, Throwable, Unit]
}
