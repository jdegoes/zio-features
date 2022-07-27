package zio.features.model

// () => Boolean
final case class TargetingRule[-Input](predicate: TargetingExpr[Input, Boolean]) { self =>
  def &&[Input1 <: Input](that: TargetingRule[Input1]): TargetingRule[Input1] = TargetingRule(
    self.predicate && that.predicate
  )

  def ||[Input1 <: Input](that: TargetingRule[Input1]): TargetingRule[Input1] = TargetingRule(
    self.predicate || that.predicate
  )

  def unary_! : TargetingRule[Input] = TargetingRule(!self.predicate)
}
object TargetingRule {
  val everyone: TargetingRule[Any] = TargetingRule(true)

  val nobody: TargetingRule[Any] = !everyone
}
