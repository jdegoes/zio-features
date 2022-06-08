package zio.features

// UserData        => Map[ExperimentId, ExperimentGroup]
// ExperimentGroup => Set[InstantiatedFeature[_]]

// 1. What are the set of experiments we are _currently_ running
//    for this environment? (CACHED - Persistent, Distributed Cache -- like Redis)
// 2. For each experiment, which group is THIS user participating in?

// {"username": "jdegoes",
//  "country": "USA", "platform": "mobile", "os": "ios", "os_version": 14.343
//  "signup_date": "10/22/2020"}

// 1. Check for equality across parameters of user data (check to see if os == "ios")
// 2. Check for inequality across parameters of user data (check to see if "os_version" > 10)
// 3. Check for case-insensitive substring matches (user-agent testing??)
//   3.1 Special case of regex matching???
// 4. Check for date/times within some window of the current date/time
// 5. Extract out and compare parts of a date/time

/*

type SelectionCriteria = (PseudoRandomSeed, UserData) => ExperimentGroup

final case class Experiment(selectionCriteria: SelectionCriteria)

{"username": "john@degoes.net", "tier": "free", powerUser": true, "platform": "ios", "device": "desktop", country": "Scotland", ...}
{"userSignupDate": "02-05-2022", ...}

FeatureA
FeatureB

30% of users in FeatureA
60% of users in FeatureB
10% of users in FeatureX (control group)



 */
// web server: ok, the user is a member of these experiments

// type InstantiatedFeature[A] = (Feature[A], ParameterValues[A])
// Experiment : User => Set[InstantiatedFeature[_]]
// Experiment1 + Experiment2 ???
//

object Example {
  val loginButtonFeature =
    Feature("login-button-3989").param[Int]("color") ?? "A new login button"

  val age      = Parameter.int("age")
  val name     = Parameter.string("name")
  val email    = Parameter.string("email")
  val platform = Parameter.string("platform")
  val os       = Parameter.string("os")
  val planType = Parameter.string("plan_type")

  import TargetingExpr._

  val androidUsers = TargetingRule(os.get === "android")
  val over21       = TargetingRule(age.get >= 21)
  val proUsers     = TargetingRule(planType.get === "pro")

  val targetingRule = androidUsers && over21 && !proUsers

  val onlyHalf = TargetingRule(random > 0.5)

  def example(ex: Experiment): Experiment =
    ex.subset(targetingRule && onlyHalf)

}
