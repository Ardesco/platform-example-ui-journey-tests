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

import org.openqa.selenium.{By, WebDriver}
import uk.gov.hmrc.test.ui.conf.TestConfiguration

object VATFlatRateService extends BasePage {

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

  val resultOutcome        = "resultOutcome"
  val useSetVATFlatRate    = "Use the 16.5% VAT flat rate"
  val useUniqueVATFlatRate = "Use the VAT flat rate for your business type"

  def goToVATFlatRateService(implicit driver: WebDriver): this.type = {
    driver.navigate().to(url)

    if (driver.getTitle != vatReturnPeriodPageTitle)
      throw PageNotFoundException(
        s"goToVATFlatRateService: Expected '$vatReturnPeriodPageTitle' page, but found '$driver.getTitle' page."
      )
    this
  }

  def provideVATPeriod(period: String)(implicit driver: WebDriver): this.type = {
    period match {
      case "Annually" => driver.findElement(By.id(annuallyRadioButton)).click()
      case _          => driver.findElement(By.id(quarterlyRadioButton)).click()
    }

    driver.findElement(By.id(continueButton)).click()
    this
  }

  def provideTurnoverAmount(amount: String)(implicit driver: WebDriver): this.type = {
    if (driver.getTitle != turnoverPageTitle)
      throw PageNotFoundException(
        s"provideVATInformation: Expected '$turnoverPageTitle' page, but found '$driver.getTitle' page."
      )

    enterTurnoverAmount(amount)
    driver.findElement(By.id(continueButton)).click()
    this
  }

  def provideCostOfGoodsAmount(amount: String)(implicit driver: WebDriver): this.type = {
    if (driver.getTitle != costOfGoodsPageTitle)
      throw PageNotFoundException(
        s"provideVATInformation: Expected '$costOfGoodsPageTitle' page, but found '$driver.getTitle' page."
      )

    enterCostOfGoodsAmount(amount)
    this
  }

  def enterTurnoverAmount(amount: String)(implicit driver: WebDriver): this.type = {
    driver.findElement(By.id(turnoverInput)).sendKeys(amount)
    this
  }

  def enterCostOfGoodsAmount(amount: String)(implicit driver: WebDriver): this.type = {
    driver.findElement(By.id(costOfGoodsInput)).sendKeys(amount)
    this
  }

  def submitVATInformation(implicit driver: WebDriver): this.type = {
    driver.findElement(By.id(continueButton)).click()
    this
  }

  def result(implicit driver: WebDriver): String = {
    if (driver.getTitle != yourVatCalculationPageTitle)
      throw PageNotFoundException(
        s"result: Expected '$yourVatCalculationPageTitle' page, but found '$driver.getTitle' page."
      )

    driver.findElement(By.id(resultOutcome)).getText
  }
}
