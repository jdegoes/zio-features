package zio.features

import zio._

trait Features extends FeatureCatalog[Any] {
  def catalog[Input](input: Data[Input]): FeatureCatalog[Input]
}
object Features {
  lazy val live: ZLayer[Any, Nothing, Features] = ???
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
      catalog = features.catalog(Data("name" -> user.name, "age" -> user.age, "email" -> user.email))
      _    <- if (catalog.isEnabled(loginButtonFeature)) ...
              else ...
      _    <- catalog.ifEnabled(loginButtonFeature) { buttonColor =>
                ...
              }
    } yield ???
}

 */
