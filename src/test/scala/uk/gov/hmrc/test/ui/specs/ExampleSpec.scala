/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.test.ui.specs

import uk.gov.hmrc.test.ui.pages.VATFlatRateService._
import uk.gov.hmrc.test.ui.specs.tags.ZapTests

class ExampleSpec extends BaseSpec {

  Feature("Examples") {

    Scenario("User is a limited cost business that pays annually and should use the 16.5% flat rate", ZapTests) {
      //Remove ZapTests tag if not required

      Given("I am on the Check your VAT flat rate service")
      goToVATFlatRateService

      And("my costs of goods are under £1000 for the year")
      provideVATPeriod("Annually")
        .provideTurnoverAmount("1000")
        .provideCostOfGoodsAmount("999")

      When("I submit my VAT information")
      submitVATInformation

      Then("I will be asked to use the 16.5% VAT flat rate")
      result should be(useSetVATFlatRate)
    }

    Scenario("User is not a limited cost business that pays annually and should use the VAT flat rate") {
      Given("I am on the Check your VAT flat rate service")
      goToVATFlatRateService

      And("my cost of goods are over £1000 for the year")
      provideVATPeriod("Annually")
        .provideTurnoverAmount("1000")
        .provideCostOfGoodsAmount("1000")

      When("I submit my VAT information")
      submitVATInformation

      Then("I will be asked to use the VAT flat rate")
      result should be(useUniqueVATFlatRate)
    }

    Scenario("User is a limited cost business that pays quarterly and should use the 16.5% flat rate") {
      Given("I am on the Check your VAT flat rate service")
      goToVATFlatRateService

      And("my costs of goods are under £250 for the quarter")
      provideVATPeriod("Quarterly")
        .provideTurnoverAmount("1000")
        .provideCostOfGoodsAmount("249")

      When("I submit my VAT information")
      submitVATInformation

      Then("I will be asked to use the 16.5% VAT flat rate")
      result should be(useSetVATFlatRate)
    }

    Scenario("User is not a limited cost business that pays quarterly and should use the VAT flat rate") {
      Given("I am on the Check your VAT flat rate service")
      goToVATFlatRateService

      And("my cost of goods are £250 or over for the quarter")
      provideVATPeriod("Quarterly")
        .provideTurnoverAmount("1000")
        .provideCostOfGoodsAmount("250")

      When("I submit my VAT information")
      submitVATInformation

      Then("I will be asked to use the VAT flat rate")
      result should be(useUniqueVATFlatRate)
    }
  }
}
