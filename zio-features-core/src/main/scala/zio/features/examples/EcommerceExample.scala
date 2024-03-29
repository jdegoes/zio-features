package zio.features.examples

import zio._
import zio.features.client.api._

object EcommerceExample {
  val lifetime_spend = ParamDescriptor.double("lifetime_spend")
  val phone_number   = ParamDescriptor.string("phone_number")
  val email          = ParamDescriptor.string("email")

  val conciergePhoneFeature: FeatureDescriptor[("lifetime_spend", Double), ("phone_number", String)] =
    FeatureDescriptor("concierge-number-001")
      .input(lifetime_spend)
      .output(phone_number) ?? "A feature that provides a concierge phone number for high-value customers"

  val conciergeEmailFeature: FeatureDescriptor[("lifetime_spend", Double), ("email", String)] =
    FeatureDescriptor("concierge-number-002")
      .input(lifetime_spend)
      .output(email) ?? "A feature that provides a concierge email for high-value customers"

  val smsNotifyPurchase: FeatureDescriptor[Any, Any] =
    FeatureDescriptor("sms-notify-purchase-001") ?? "A feature that sends an SMS notification when a purchase is made"

  import TargetingExpr._

  // TODO: Create an API for CRUD'ing these rules + features:
  val highValueCustomers = lifetime_spend.get > 10000.0

  val fiftyPercent = TargetingExpr.random < 0.5

  (FeatureDecisionMatrix.enable(conciergePhoneFeature).when(fiftyPercent) ||
    FeatureDecisionMatrix.enable(conciergeEmailFeature)).when(highValueCustomers)

  trait Database

  // /company/contact
  // {"address": "221 B Baker Street", "phone_number": "123-456-7890"}
  class CompanyDataService(database: Database, features: Features) {
    def contactDetails: Task[ContactDetails] =
      for {
        lifetime_spend <- lifetimeSpend
        catalog         = features.catalog(Params("lifetime_spend", lifetime_spend))
        base           <- baseContactDetails
      } yield catalog
        .check(conciergePhoneFeature)
        .map(data => base.withPhoneNumber(data.get(phone_number)))
        .getOrElse(base)

    def baseContactDetails: Task[ContactDetails] = ZIO.succeed(ContactDetails("221 B Baker Street", None))

    def lifetimeSpend: Task[Double] = ???
  }

  class PurchaseService(database: Database, features: Features) {
    def completePurchase(purchase: Purchase): Task[Unit] =
      features.basic.ifEnabled(smsNotifyPurchase)(_ => sendSmsNotification(purchase)) *> ZIO.unit

    def sendSmsNotification(purchase: Purchase): Task[Unit] =
      ???
  }

  final case class Purchase()

  final case class ContactDetails(address: String, phoneNumber: Option[String]) {
    def withPhoneNumber(phoneNumber: String): ContactDetails = copy(phoneNumber = Some(phoneNumber))
  }

}
