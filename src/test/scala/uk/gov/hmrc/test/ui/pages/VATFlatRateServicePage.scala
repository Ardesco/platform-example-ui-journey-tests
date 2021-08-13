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

package uk.gov.hmrc.test.ui.pages

import org.openqa.selenium.WebDriver
import uk.gov.hmrc.test.ui.conf.TestConfiguration

object VATFlatRateServicePage extends BasePage {

  val url: String = TestConfiguration.url("example-frontend") + "/vat-return-period"
  println(s"Url to VAT flat rate service is: $url")

  val vatReturnPeriodPageTitle    = "Enter your VAT return details"
  val turnoverPageTitle           = "Enter your turnover"
  val costOfGoodsPageTitle        = "Enter your cost of goods"
  val yourVatCalculationPageTitle = "Your VAT calculation"

  val annuallyRadioButton  = "vatReturnPeriod-annually"
  val quarterlyRadioButton = "vatReturnPeriod-quarterly"

  val turnoverInput    = "turnover"
  val costOfGoodsInput = "costOfGoods"

  val continueButton = "continue-button"

  val resultOutcome     = "resultOutcome"
  val setVATFlatRate    = "Use the 16.5% VAT flat rate"
  val uniqueVATFlatRate = "Use the VAT flat rate for your business type"

  def goToVATFlatRateService(implicit driver: WebDriver): Unit = {
    go to VATFlatRateServicePage
    pageTitle shouldBe vatReturnPeriodPageTitle
  }

  def provideVATInformation(period: String, turnoverAmount: String, costOfGoodsAmount: String)(implicit
    driver: WebDriver
  ): Unit = {
    period match {
      case "Annually" => click on annuallyRadioButton
      case _          => click on quarterlyRadioButton
    }

    click on continueButton
    pageTitle shouldBe turnoverPageTitle

    enterTurnoverAmount(turnoverAmount)
    click on continueButton
    pageTitle shouldBe costOfGoodsPageTitle

    enterCostOfGoodsAmount(costOfGoodsAmount)
  }

  def enterTurnoverAmount(amount: String)(implicit driver: WebDriver): Unit =
    textField(turnoverInput).value = amount

  def enterCostOfGoodsAmount(amount: String)(implicit driver: WebDriver): Unit =
    textField(costOfGoodsInput).value = amount

  def submitVATInformation(implicit driver: WebDriver): Unit = {
    click on continueButton
    pageTitle shouldBe yourVatCalculationPageTitle
  }

  def result(implicit driver: WebDriver): String =
    id("resultOutcome").element.text

}
