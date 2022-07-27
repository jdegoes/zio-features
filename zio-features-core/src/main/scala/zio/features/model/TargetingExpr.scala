package zio.features.model

import scala.language.implicitConversions

// Mental Model:
//
// type TargetingExpr[-Input, +A] == Data[Input] => A
sealed trait TargetingExpr[-Input, +Out] { self =>
  final def &&[Input1 <: Input](that: TargetingExpr[Input1, Boolean])(implicit
    ev: Out <:< Boolean
  ): TargetingExpr[Input1, Boolean] =
    TargetingExpr.Conjunction(self.widen[Boolean], that)

  final def ||[Input1 <: Input](that: TargetingExpr[Input1, Boolean])(implicit
    ev: Out <:< Boolean
  ): TargetingExpr[Input1, Boolean] =
    TargetingExpr.Disjunction(self.widen[Boolean], that)

  def >[Input1 <: Input, Out1 >: Out](that: TargetingExpr[Input1, Out1]): TargetingExpr[Input1, Boolean] =
    TargetingExpr.GreaterThan(self.widen[Out1], that)

  def >=[Input1 <: Input, Out1 >: Out](that: TargetingExpr[Input1, Out1]): TargetingExpr[Input1, Boolean] =
    (self > that) || (self == that)

  def <[Input1 <: Input, Out1 >: Out](that: TargetingExpr[Input1, Out1]): TargetingExpr[Input1, Boolean] =
    TargetingExpr.LessThan(self.widen[Out1], that)

  def <=[Input1 <: Input, Out1 >: Out](that: TargetingExpr[Input1, Out1]): TargetingExpr[Input1, Boolean] =
    (self < that) || (self == that)

  def ===[Input1 <: Input, Out1 >: Out](that: TargetingExpr[Input1, Out1]): TargetingExpr[Input1, Boolean] =
    TargetingExpr.Equals(self.widen[Out1], that)

  final def widen[Out2](implicit ev: Out <:< Out2): TargetingExpr[Input, Out2] =
    self.asInstanceOf[TargetingExpr[Input, Out2]]

  final def unary_!(implicit ev: Out <:< Boolean): TargetingExpr[Input, Boolean] =
    TargetingExpr.Negation(self.widen[Boolean])
}
object TargetingExpr {
  private[features] case object Random extends TargetingExpr[Any, Double]
  private[features] final case class Equals[Input, Type](
    left: TargetingExpr[Input, Type],
    right: TargetingExpr[Input, Type]
  ) extends TargetingExpr[Input, Boolean]
  private[features] final case class GreaterThan[Input, Type](
    left: TargetingExpr[Input, Type],
    right: TargetingExpr[Input, Type]
  ) extends TargetingExpr[Input, Boolean]
  private[features] final case class LessThan[Input, Type](
    left: TargetingExpr[Input, Type],
    right: TargetingExpr[Input, Type]
  ) extends TargetingExpr[Input, Boolean]
  private[features] final case class Literal[Type](value: Type, paramType: ParamType[Type])
      extends TargetingExpr[Any, Type]

  private[features] final case class Conjunction[Input](
    left: TargetingExpr[Input, Boolean],
    right: TargetingExpr[Input, Boolean]
  ) extends TargetingExpr[Input, Boolean]
  private[features] final case class Disjunction[Input](
    left: TargetingExpr[Input, Boolean],
    right: TargetingExpr[Input, Boolean]
  ) extends TargetingExpr[Input, Boolean]
  private[features] final case class Negation[Input](rule: TargetingExpr[Input, Boolean])
      extends TargetingExpr[Input, Boolean]
  private[features] final case class Extraction[KeyValue, Type](paramDescriptor: ParamDescriptor[KeyValue])
      extends TargetingExpr[KeyValue, Type]

  implicit def literal[Type](value: Type)(implicit paramType: ParamType[Type]): TargetingExpr[Any, Type] =
    Literal(value, paramType)

  def random: TargetingExpr[Any, Double] = Random
}
