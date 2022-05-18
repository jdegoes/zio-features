package zio.features

// Type => Boolean
sealed trait Predicate[Type]
object Predicate {
  // Double => Boolean
  final case class Random[Type](predicate: Predicate[Double], paramType: ParamType[Type]) extends Predicate[Type]
  final case class Equals[Type](value: Type, paramType: ParamType[Type])                  extends Predicate[Type]
  final case class GreaterThan[Type](value: Type, paramType: ParamType[Type])             extends Predicate[Type]
  final case class LessThan[Type](value: Type, paramType: ParamType[Type])                extends Predicate[Type]

  def equals[Type](constant: Type)(implicit paramType: ParamType[Type]): Predicate[Type] = Equals(constant, paramType)

  def greaterThan[Type](constant: Type)(implicit paramType: ParamType[Type]): Predicate[Type] =
    GreaterThan(constant, paramType)

  def lessThan[Type](constant: Type)(implicit paramType: ParamType[Type]): Predicate[Type] =
    LessThan(constant, paramType)

  def random[Type](p: Predicate[Double])(implicit paramType: ParamType[Type]): Predicate[Type] = Random(p, paramType)
}
