object ScalaTrainingDay_2 {
  println("Welcome to the Scala worksheet");      //> Welcome to the Scala worksheet
  
  var number : Int = 10;                          //> number  : Int = 10
  
  while (number>=0) {
  	//println(number);
  	number = number-1;
  }
  
  def square(x:Int) : Int = {
  	x*x;
  }                                               //> square: (x: Int)Int
  
  println(square(3));                             //> 9
  
  def funcInsideFunc( i : Int , f : Int => Int) : Int = {
  	f(i)
  }                                               //> funcInsideFunc: (i: Int, f: Int => Int)Int
  
  println(funcInsideFunc(4,square));              //> 16
  
  println(funcInsideFunc(12,a => {a*a}))          //> 144
  
  val namesTuple = ("ramesh","suresh","kartos")   //> namesTuple  : (String, String, String) = (ramesh,suresh,kartos)
  
  val randomDataList = List("t1","t2","t3")       //> randomDataList  : List[String] = List(t1, t2, t3)
  
  println(namesTuple._1)                          //> ramesh
  
  println(randomDataList(0))                      //> t1
  
  println(randomDataList.head)                    //> t1
  
  val reverseRandomDataList = randomDataList.map( (x: String) => { x.reverse} )
                                                  //> reverseRandomDataList  : List[String] = List(1t, 2t, 3t)
                                                  
  val numbersList = List(1,2,3,4,5,6,7)           //> numbersList  : List[Int] = List(1, 2, 3, 4, 5, 6, 7)
  
  val sumList = numbersList.reduce((a:Int,b:Int) => a+b);
                                                  //> sumList  : Int = 28
                                                  
  val noFives = numbersList.filter ( (x) => x!= 5);
                                                  //> noFives  : List[Int] = List(1, 2, 3, 4, 6, 7)
                                                  
  val sumLists = noFives ++ numbersList;          //> sumLists  : List[Int] = List(1, 2, 3, 4, 6, 7, 1, 2, 3, 4, 5, 6, 7)
  
  val soretedList = sumLists.sorted;              //> soretedList  : List[Int] = List(1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7)
  
  
}