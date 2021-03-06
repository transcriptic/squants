/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.motion

import squants._
import squants.time.{ Seconds, TimeDerivative }
import squants.mass.Kilograms

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 * @param mass Mass
 * @param time Time
 */
case class MassFlowRate(mass: Mass, time: Time) extends Quantity[MassFlowRate] with TimeDerivative[Mass] {

  def valueUnit = KilogramsPerSecond
  def value = toKilogramsPerSecond
  def change = mass

  def toKilogramsPerSecond = mass.toKilograms / time.toSeconds
}

trait MassFlowRateUnit extends UnitOfMeasure[MassFlowRate] {
}

object KilogramsPerSecond extends MassFlowRateUnit with ValueUnit {
  def apply(d: Double) = MassFlowRate(Kilograms(d), Seconds(1))
  val symbol = "kg/s"
}

object MassFlowRateConversions {
  lazy val kilogramPerSecond = KilogramsPerSecond(1)

  implicit class MassFlowRateConversions(val d: Double) {
    def kilogramsPerSecond = KilogramsPerSecond(d)
  }

  implicit object MassFlowRateNumeric extends AbstractQuantityNumeric[MassFlowRate](KilogramsPerSecond)
}
