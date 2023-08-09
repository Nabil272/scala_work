package com.firstCourse

import java.util.Date
import Array._

class helloWorldMain {
}


object helloWorld {

  val name = "nabil"
  // lazy val is not stored in memory until used
  lazy val x = 4

  def main(args: Array[String]): Unit = {
    // string interpolation
    println(s"hello $name ! ")

    // if else statement , and {} operator
    if (x == 4) {
      val y = {
        val premier = 1;
        val deuxieme = 2;
        premier + deuxieme
      }

    }

    // better coding ==>
    val y = if (x == 3) {
      val premier = 1;
      val deuxieme = 2;
      premier + deuxieme
    }



    // for loop
    //for (i <- 1 to 5) { //print("ok")
    // double for
    //for (i <- 1 to 5; j <-1 to 5)  //println(s"$i $j")

    // foreach loop
    //val z = List("nabil", "hadji")
    //for (ele <- z; if ele=="nabil") println(ele)

    // store result of a for loop
    //val result = for(ele <- 1 to 3) yield ele*2
    //print(result(2))


    x match {
      case 3 => println("bingo")
      case 2 => println("pas bingo")
      case _ => println("ok je vois ")
    }

    val matchUseCase = x match {
      case 3 => x
      case 2 => x
      case _ => "default"
    }

    val multiMatch = x match {
      case 3 | 2 | 1 | 0 => x
      case _ => "default"
    }

    val added = add(1,2)

  }

  def add(x:Int=2 , y:Int): Int ={
    return x+y
  }

  // Higher order functions

  def math(x:Int , y:Int , func: (Int,Int)=>Int) : Int = func(x,y)

  var ad = math(1, 2, (x,y)=> x+y)


  def complicatedMath(x: Int, y: Int, z: Int, func: (Int, Int) => Int): Int = func(func(x, y), z)

  //println(complicatedMath(1, 2, 3, _ + _))


  val anonym = (a: Int , b: Int , c: Int ) => (a+b+c)*2
  // partially applied functions , définir une fonction à partir d'une autre , en figeant les arguments
  val f = anonym(10, 20 , _:Int)


  def log(message : String ) : Unit = {
    val nowDate = new Date;
    println(s"$nowDate $message")
  }

  //log("ceci est un message")
  //Thread.sleep(30000)
  //log("ceci est le deuxieme message")


  // closures
  val toto = 2

  def monToto(x: Int ):Int = {
    return toto * 2
  }


  // Currying functions

  def curFunc (x:Int) = (y:Int) => x+y

  val b = curFunc(2)
  // créer une variable en lui passant tout d'abord qu'un seul argument
  //println(b)
  // ensuite passer le deuxieme
  val c= b(10)
  //println(c)

  /* POURQUOI FAIRE DU CURRYING EN PROGRAMMATION FONCTIONNELLE ?
  1) Réutilisation de code : Comme nous l'avons vu dans l'exemple ci-dessus, le "currying" peut nous permettre
  de générer de nouvelles fonctions que nous pouvons réutiliser. Dans l'exemple, b est une fonction que
   nous pouvons réutiliser pour ajouter 2 à n'importe quel nombre.

  2) Abstraction de comportement : Le "currying" peut nous aider à abstraire certains comportements.
   Par exemple, nous pourrions définir une fonction filter qui prend une liste et une fonction de prédicat,
    et renvoie une nouvelle liste avec seulement les éléments qui satisfont le prédicat. Avec le "currying",
     nous pourrions créer de nouvelles fonctions filter qui ont un prédicat spécifique déjà fixé.

  3) Syntaxe plus agréable : Dans certains cas, le "currying" peut rendre la syntaxe du code plus agréable.
   Par exemple, en Scala, si une fonction prend un seul argument, nous pouvons utiliser une syntaxe spéciale
    pour appeler cette fonction. Cela peut rendre le code plus lisible dans certains cas.
   */

  // autre syntaxe (plus utilisée)
  def curFunc2(x: Int) (y:Int) = x+y
  val a= curFunc2(2)_


  //  ARRAYS
  // tableau initialisé par défaut à des valeurs 0
  val h : Array[Int] = new Array[Int](4)
  val g = new Array[Int](5)

  h(0) = 20
  h(1) = 40

  //println(concat(h, g))
  for (e <- concat(h, g)){
     //println(e)
  }


  // LISTS

  val nn : List[Int] = List(1,2,3)
  val uu: List[Any]= List("nabil",9, true)

  // MAPS

  val dossier : Map[String , Any] = Map("nom"->"nabil", "age"->24)

  // mauvaise habitude
  dossier("nom")

  // ==> meilleur


  val maValeur = dossier.get("nom") match{
    case Some(value :String) => value
    case _ => false
  }

  // MAP ==> DICTIONNAIRE PYTHON
  //println(dossier.keys)  // clés
  //println(dossier.values) // values
  //println(dossier.isEmpty)


  val tutu = (1, 3, 7)

  //println(tutu._1)
  //println(tutu._2)
  //println(tutu._3)
  //println(tutu._4)

  // il faut rajouter .productIterator pour pouvoir boucler dessus
  //for (e <- tutu.productIterator) println(e)

  // productIterator renvoie une collection de type Any , donc ne peut pas etre comparé avec >
  //tutu.productIterator.toList.filter(p=> p>1)

  // il faut donc ajouter une condition de cast
  val lol =tutu.productIterator.filter{
    case i : Int => i>1
    case _ => false
  }.toList





  // OPTIONS type ==> options retourne soit Some() soit None

  // find retourne un type Option
  val o= lol.find(_==2) match{
    case Some(value) => value
    case None => false
  }



  //println(lol.find(_==2).getOrElse("pas dispo"))



  // MAP FUNCTION

  val zozo : List[Double] = List(1,8,9,4,7,3.3,9,7,3)
  val momo : Map[String, Double] = Map("science" -> 20, "physique" -> 18, "math"-> 17)
  val abc : List[String] = List("A","B","C")

  //println(zozo.map(_ *2))
  //println(momo.mapValues(_ - 2))


  // difference entre map et flatMap , c'est que flatMap flat sa sortie

  val t= zozo.flatMap(x=> List(x))
  //println(t)

  //println(zozo.filter(_.isInstanceOf[Double]))

  //println(abc.reduceLeft(_+_))


  // une higher fonction qui prend une autre fonction et applique cette autre fonction au premier argument de la higher
  def calcAnything(number: Int, calcFunction: Int => Int): Int = calcFunction(number)

  def addition(x1: Int, x2: Int): Int = x1+x2
  def sous(x1:Int , x2:Int): Int = x1-x2
  def fois(x1:Int , x2:Int) : Int = x1*x2

  val wo= calcAnything(10, addition(_,2))
  val vo = calcAnything(10 , sous(_,10))

  assert (wo == 12)

  def artimeticOps(x1: Int , x2:Int , ops : String)= {

    ops match {
      case "addition" => addition(x1,x2)
      case "sous" => sous(x1,x2)
      case "fois" => fois(x1,x2)
      case _ => "opération non prise en compte"
    }
  }


  val multiplication: (Int, Int) => Int = (x, y) => x * y


  val curriedMultiplication: Int => Int => Int = x => y => x * y

  // pour l'appeller il faut soit faire ça
  val hohi = curriedMultiplication(2)
  val hiho = hohi(4)
  // ou ça
  val tz = curriedMultiplication(2)(5)

  //println(hohi)
  //println(hiho)

















}

