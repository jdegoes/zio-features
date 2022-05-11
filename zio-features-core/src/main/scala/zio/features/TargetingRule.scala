package zio.features

// UserData => Boolean
sealed trait TargetingRule { self =>
  def &&(that: TargetingRule): TargetingRule = TargetingRule.Conjunction(self, that)
}
object TargetingRule {
  case object Everyone                                                    extends TargetingRule
  final case class Conjunction(left: TargetingRule, right: TargetingRule) extends TargetingRule

  lazy val everyone: TargetingRule = Everyone
}
