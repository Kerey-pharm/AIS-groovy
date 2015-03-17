package kz.kerey.scala.services

/**
 * Created by Alina on 3/17/15.
 */
class Human(fullname: String, ages: Int, sexs: Boolean) extends named with moveable {

  override def name(): String = {
    fullname
  }

  override def age(): Int = {
    ages
  }

  override def sex(): Boolean = {
    sexs
  }

  override def move(): Unit = {
    println("just moved person")
  }
}
