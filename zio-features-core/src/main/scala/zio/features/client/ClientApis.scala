package zio.features.client

import zio._
import zio.features.common.model._

trait ClientApis {
  trait FeatureCatalog[Input] {
    final def isEnabled[Input1 >: Input](fd: FeatureDescriptor[Input1, _]): Boolean = check(fd).isDefined

    final def isDisabled[Input1 >: Input](fd: FeatureDescriptor[Input1, _]): Boolean =
      !isEnabled(fd)

    def check[Input1 >: Input, Output](fd: FeatureDescriptor[Input1, Output]): Option[Params[Output]]

    final def ifEnabled[Input1 >: Input, R, E, A, B](fd: FeatureDescriptor[Input1, A])(
      body: Params[A] => ZIO[R, E, B]
    ): ZIO[R, E, Option[B]] = ZIO.fromOption(check(fd)).flatMap(a => body(a).mapError(Some(_))).unsome

    final def ifDisabled[Input1 >: Input, R, E, A](fd: FeatureDescriptor[Input1, _])(
      body: => ZIO[R, E, A]
    ): ZIO[R, E, Option[A]] =
      if (isDisabled(fd)) body.map(Some(_)) else ZIO.none
  }

  trait Features {
    def catalog[Input](input: Params[Input]): FeatureCatalog[Input]

    val basic: FeatureCatalog[Any] = catalog(Params.empty)
  }
}
