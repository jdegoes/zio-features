package zio.features

final case class NonEmptySet[A](value: A, values: Set[A])