package zio.features.common.model

import zio.test._ 
import zio.test.TestAspect._

object TargetingExprSpec extends ZIOSpecDefault {
  import TargetingExpr._ 

  def spec = 
    suite("TargetingExprSpec") {
      suite("execution") {
        test("literal") {
          for {
            result <- literal(42).execute(Params.empty)
          } yield assertTrue(result == 42)
        } + 
        test("equality (positive)") {
          for {
            result <- (literal(42) === literal(42)).execute(Params.empty)
          } yield assertTrue(result == true)
        } + 
        test("equality (negative)") {
          for {
            result <- (literal(42) === literal(24)).execute(Params.empty)
          } yield assertTrue(result == false)
        } + 
        test("currentTime") {
          for {
            result <- currentTime.execute(Params.empty)
          } yield assertTrue(result == java.time.Instant.ofEpochSecond(0L))
        } + 
        test("randomOf") {
          for {
            result <- randomOf("sherlock@holmes.com").execute(Params.empty)
          } yield assertTrue(result == 0.4948122872329044)
        }
      }
    }
}