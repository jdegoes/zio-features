package zio.features

import scala.language.implicitConversions

// Mental Model:
//
// type TargetingExpr[+A] == () => A
sealed trait TargetingExpr[+Out] { self =>
  final def &&(that: TargetingExpr[Boolean])(implicit ev: Out <:< Boolean): TargetingExpr[Boolean] =
    TargetingExpr.Conjunction(self.widen[Boolean], that)

  final def ||(that: TargetingExpr[Boolean])(implicit ev: Out <:< Boolean): TargetingExpr[Boolean] =
    TargetingExpr.Disjunction(self.widen[Boolean], that)

  final def widen[Out2](implicit ev: Out <:< Out2): TargetingExpr[Out2] =
    self.asInstanceOf[TargetingExpr[Out2]]

  final def unary_!(implicit ev: Out <:< Boolean): TargetingExpr[Boolean] =
    TargetingExpr.Negation(self.widen[Boolean])
}
object TargetingExpr {
  private[features] case object Random extends TargetingExpr[Double]
  private[features] final case class Equals[Type](left: TargetingExpr[Type], right: TargetingExpr[Type])
      extends TargetingExpr[Boolean]
  private[features] final case class GreaterThan[Type](left: TargetingExpr[Type], right: TargetingExpr[Type])
      extends TargetingExpr[Boolean]
  private[features] final case class LessThan[Type](left: TargetingExpr[Type], right: TargetingExpr[Type])
      extends TargetingExpr[Boolean]
  private[features] final case class Literal[Type](value: Type, paramType: ParamType[Type]) extends TargetingExpr[Type]

  private[features] final case class Conjunction(left: TargetingExpr[Boolean], right: TargetingExpr[Boolean])
      extends TargetingExpr[Boolean]
  private[features] final case class Disjunction(left: TargetingExpr[Boolean], right: TargetingExpr[Boolean])
      extends TargetingExpr[Boolean]
  private[features] final case class Negation(rule: TargetingExpr[Boolean])       extends TargetingExpr[Boolean]
  private[features] final case class Extraction[Type](parameter: Parameter[Type]) extends TargetingExpr[Type]

  def equalTo[Type](left: TargetingExpr[Type], right: TargetingExpr[Type]): TargetingExpr[Boolean] =
    Equals(left, right)

  def extract[Type](parameter: Parameter[Type]): TargetingExpr[Type] =
    Extraction(parameter)

  def greaterThan[Type](left: TargetingExpr[Type], right: TargetingExpr[Type]): TargetingExpr[Boolean] =
    GreaterThan(left, right)

  def lessThan[Type](left: TargetingExpr[Type], right: TargetingExpr[Type]): TargetingExpr[Boolean] =
    LessThan(left, right)

  implicit def literal[Type](value: Type)(implicit paramType: ParamType[Type]): TargetingExpr[Type] =
    Literal(value, paramType)

  def random: TargetingExpr[Double] = Random
}
