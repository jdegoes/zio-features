package zio.features.cli

import zio._

object FeaturesCLI extends ZIOAppDefault {
  val run = Console.printLine("Hello World!")
}
