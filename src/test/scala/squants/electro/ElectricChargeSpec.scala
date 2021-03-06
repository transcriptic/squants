/*                                                                      *\
** Squants                                                              **
**                                                                      **
** Scala Quantities and Units of Measure Library and DSL                **
** (c) 2013-2014, Gary Keorkunian                                       **
**                                                                      **
\*                                                                      */

package squants.electro

import org.scalatest.{ Matchers, FlatSpec }
import squants.MetricSystem
import squants.time.{ Seconds, Time }
import squants.energy.Joules
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

/**
 * @author  garyKeorkunian
 * @since   0.1
 *
 */
class ElectricChargeSpec extends FlatSpec with Matchers {

  behavior of "ElectricalCharge and its Units of Measure"

  it should "create values using UOM factories" in {
    Coulombs(1).toCoulombs should be(1)
    Picocoulombs(1).toPicocoulombs should be(1)
    Nanocoulombs(1).toNanocoulombs should be(1)
    Microcoulombs(1).toMicrocoulombs should be(1)
    Millicoulombs(1).toMillcoulombs should be(1)
    Abcoulombs(1).toAbcoulombs should be(1)
    AmpereHours(1).toAmpereHours should be(1)
  }

  it should "properly convert to all supported Units of Measure" in {
    val x = Coulombs(1)
    x.toCoulombs should be(1.0)
    x.toNanocoulombs should be(1 / MetricSystem.Nano)
    x.toMicrocoulombs should be(1 / MetricSystem.Micro)
    x.toMillcoulombs should be(1 / MetricSystem.Milli)
    x.toAbcoulombs should be(1 / MetricSystem.Deca)
    x.toAmpereHours should be(1 / Time.SecondsPerHour)
  }

  it should "return properly formatted strings for all supported Units of Measure" in {
    Coulombs(1).toString(Coulombs) should be("1.0 C")
    Nanocoulombs(1).toString(Nanocoulombs) should be("1.0 nC")
    Microcoulombs(1).toString(Microcoulombs) should be("1.0 µC")
    Millicoulombs(1).toString(Millicoulombs) should be("1.0 mC")
    Abcoulombs(1).toString(Abcoulombs) should be("1.0 aC")
    AmpereHours(1).toString(AmpereHours) should be("1.0 Ah")
  }

  it should "return Energy when multiplied by ElectricalPotential" in {
    Coulombs(1) * Volts(1) should be(Joules(1))
  }

  it should "return ElectricalCurrent when divided by Time" in {
    Coulombs(1) / Seconds(1) should be(Amperes(1))
  }

  it should "return Capacitance when divided by ElectricalPotential" in {
    Coulombs(1) / Volts(1) should be(Farads(1))
  }

  it should "return ElectricalPotential when divided by Capacitance" in {
    Coulombs(1) / Farads(1) should be(Volts(1))
  }

  it should "return Time when divided by ElectricalCurrent" in {
    Coulombs(1) / Amperes(1) should be(Seconds(1))
  }

  it should "serialize to and de-serialize from Json" in {
    implicit val formats = DefaultFormats
    val x = Coulombs(10.22)
    val ser = Serialization.write(x)
    val des = Serialization.read[ElectricCharge](ser)
    x should be(des)
  }

  behavior of "ElectricalChargeConversions"

  it should "provide aliases for single unit values" in {
    import ElectricChargeConversions._

    coulomb should be(Coulombs(1))
    picocoulomb should be(Picocoulombs(1))
    nanocoulomb should be(Nanocoulombs(1))
    microcoulomb should be(Microcoulombs(1))
    millicoulomb should be(Millicoulombs(1))
    abcoulomb should be(Abcoulombs(1))
    ampereHour should be(AmpereHours(1))
  }

  it should "provide implicit conversion from Double" in {
    import ElectricChargeConversions._

    val d = 10d
    d.coulombs should be(Coulombs(d))
    d.picocoulombs should be(Picocoulombs(d))
    d.nanocoulombs should be(Nanocoulombs(d))
    d.microcoulombs should be(Microcoulombs(d))
    d.millicoulombs should be(Millicoulombs(d))
    d.abcoulombs should be(Abcoulombs(d))
    d.ampereHours should be(AmpereHours(d))
  }

  it should "provide Numeric support" in {
    import ElectricChargeConversions.ElectricalChargeNumeric

    val ecs = List(Coulombs(1), Millicoulombs(100))
    ecs.sum should be(Millicoulombs(1100))
  }
}
