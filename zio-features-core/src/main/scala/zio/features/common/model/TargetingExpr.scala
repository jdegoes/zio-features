package zio.features.common.model

import scala.language.implicitConversions

import zio._

// Mental Model:
//
// type TargetingExpr[-Input, +Out] == Params[Input] => Out
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

  def execute[Input1 <: Input](input: Params[Input1]): ZIO[Any, Nothing, Out] =
    TargetingExpr.execute[Input1, Out](self, input)

  final def widen[Out2](implicit ev: Out <:< Out2): TargetingExpr[Input, Out2] =
    self.asInstanceOf[TargetingExpr[Input, Out2]]

  final def unary_!(implicit ev: Out <:< Boolean): TargetingExpr[Input, Boolean] =
    TargetingExpr.Negation(self.widen[Boolean])
}
object TargetingExpr {
  private[features] case class Random[Input](seed: TargetingExpr[Input, _]) extends TargetingExpr[Input, Double]
  private[features] case object CurrentTime                                 extends TargetingExpr[Any, java.time.Instant]
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

  def currentTime: TargetingExpr[Any, java.time.Instant] = CurrentTime

  def random: TargetingExpr[Any, Double] = randomOf(0)

  def randomOf[A](value: A)(implicit paramType: ParamType[A]): TargetingExpr[Any, Double] = randomOf(
    Literal(value, paramType)
  )

  def randomOf[Input, A](seed: TargetingExpr[Input, A]): TargetingExpr[Input, Double] = Random(seed)

  import zio._
  import java.time.Instant

  private[features] def execute[Input, Out](
    expr: TargetingExpr[Input, Out],
    params: Params[Input]
  ): ZIO[Any, Nothing, Out] = {
    def loop[Out](now: Instant, expr: TargetingExpr[Input, Out]): ZIO[Any, Nothing, Out] =
      expr match {
        case Random(seedExpr) =>
          for {
            seed   <- loop(now, seedExpr)
            random <- ZIO.succeed(zio.Random.RandomScala(new scala.util.Random(seed.hashCode.toLong)))
            dbl    <- random.nextDouble
          } yield dbl

        case CurrentTime => ZIO.succeed(now)

        case Equals(left, right) =>
          for {
            left  <- loop(now, left)
            right <- loop(now, right)
          } yield left == right

        case GreaterThan(left, right) => ???

        case LessThan(left, right) => ???

        case Literal(value, _) => ZIO.succeed(value)

        case Conjunction(left, right) =>
          for {
            left  <- loop(now, left)
            right <- loop(now, right)
          } yield left && right

        case Disjunction(left, right) =>
          for {
            left  <- loop(now, left)
            right <- loop(now, right)
          } yield left || right

        case Negation(rule) =>
          for {
            rule <- loop(now, rule)
          } yield !rule

        case Extraction(paramDescriptor) =>
          for {
            value <- ZIO
                       .fromOption(params.unsafe.get(paramDescriptor)(Unsafe.unsafe))
                       .orDieWith(_ => new Exception(s"Missing param ${paramDescriptor} in input ${params}"))
          } yield value.asInstanceOf[Out]
      }

    for {
      now    <- Clock.instant
      result <- loop(now, expr)
    } yield result
  }

}
