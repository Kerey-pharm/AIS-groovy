package kz.kerey.scala.services

object RESTService {

  def fact(n: Int): Int = {
    if (n<2) 1 else fact(n-1)+n
  }

  def human(n:Int): Human = {
    new Human("daka",28,true)
  }

  def main(args: Array[String]) = {
    println(fact(10))
    human(20).move()
    println(human(10).age())
  }

}
