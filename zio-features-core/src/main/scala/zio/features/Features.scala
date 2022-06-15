package zio.features

import zio._ 

trait Features {
  def isEnabled(fd: FeatureDescriptor[_]): Boolean

  final def isDisabled(fd: FeatureDescriptor[_]): Boolean = 
    !isEnabled(fd)

  def ifEnabled[R, E, A, B](fd: FeatureDescriptor[A])(
    body: A => ZIO[R, E, B]): ZIO[R, E, Option[B]]

  final def ifDisabled[R, E, A](fd: FeatureDescriptor[_])(
    body: => ZIO[R, E, A]): ZIO[R, E, Option[A]] = 
      if (isDisabled(fd)) body.map(Some(_)) else ZIO.none
}
object Features {
  lazy val live: ZLayer[Any, Nothing, Features] = ???
}
final case class FeaturesLive() extends Features {
  def isEnabled(fd: FeatureDescriptor[_]): Boolean = ???

  def ifEnabled[R, E, A, B](fd: FeatureDescriptor[A])(
    body: A => ZIO[R, E, B]): ZIO[R, E, Option[B]] = ???
}

/*
feature.get("<name>").isActive
feature.isActive("Feature1", "Feature2")
featureService <- ZIO.service[Features]
featureService.features {
  case SomeFeature() => whatever
}
buttonColor <- feature.getValue("buttonColor")

class ProfileEnrichmentService(features: Features, userRepo: UserRepo) {
  def enrich: ZIO[Any, Throwable, Unit] = 
    for {
      user <- userRepo.getUserById("foo")
      _    <- if (features.isEnabled(loginButtonFeature)) ... 
              else ... 
      _    <- features.ifEnabled(loginButtonFeature) { buttonColor =>
                ...
              }
    } yield ???
}

*/