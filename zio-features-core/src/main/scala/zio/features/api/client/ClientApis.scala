package zio.features.api.client

import zio._
import zio.features.model._

trait ClientApis {
  trait Features extends FeatureCatalog[Any] {
    def catalog[Input](input: Data[Input]): FeatureCatalog[Input]
  }
  object Features {
    lazy val live: ZLayer[Any, Nothing, Features] = ???
  }
}
