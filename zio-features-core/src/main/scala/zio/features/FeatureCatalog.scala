package zio.features

import zio._

trait FeatureCatalog[Input] {
  def isEnabled[Input1 >: Input](fd: FeatureDescriptor[Input1, _]): Boolean

  final def isDisabled[Input1 >: Input](fd: FeatureDescriptor[Input1, _]): Boolean =
    !isEnabled(fd)

  def ifEnabled[Input1 >: Input, R, E, A, B](fd: FeatureDescriptor[Input1, A])(
    body: A => ZIO[R, E, B]
  ): ZIO[R, E, Option[B]]

  final def ifDisabled[Input1 >: Input, R, E, A](fd: FeatureDescriptor[Input1, _])(
    body: => ZIO[R, E, A]
  ): ZIO[R, E, Option[A]] =
    if (isDisabled(fd)) body.map(Some(_)) else ZIO.none
}
