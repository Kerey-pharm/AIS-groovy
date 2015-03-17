package kz.kerey.scala.services

/**
 * Created by Alina on 3/17/15.
 */
trait driveable {
  def drive()
}

trait moveable {
  def move()
}

trait flyable {
  def fly()
}

trait named {
  def name():String
  def age():Int
  def sex():Boolean
}