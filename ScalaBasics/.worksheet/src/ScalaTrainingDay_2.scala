object ScalaTrainingDay_2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(72); 
  println("Welcome to the Scala worksheet");$skip(28); ;
  
  var number : Int = 10;System.out.println("""number  : Int = """ + $show(number ));$skip(73); ;
  
  while (number>=0) {
  	//println(number);
  	number = number-1;
  };$skip(45); 
  
  def square(x:Int) : Int = {
  	x*x;
  };System.out.println("""square: (x: Int)Int""");$skip(25); 
  
  println(square(3));$skip(73); ;
  
  def funcInsideFunc( i : Int , f : Int => Int) : Int = {
  	f(i)
  };System.out.println("""funcInsideFunc: (i: Int, f: Int => Int)Int""");$skip(40); 
  
  println(funcInsideFunc(4,square));$skip(44); ;
  
  println(funcInsideFunc(12,a => {a*a}));$skip(51); 
  
  val namesTuple = ("ramesh","suresh","kartos");System.out.println("""namesTuple  : (String, String, String) = """ + $show(namesTuple ));$skip(47); 
  
  val randomDataList = List("t1","t2","t3");System.out.println("""randomDataList  : List[String] = """ + $show(randomDataList ));$skip(28); 
  
  println(namesTuple._1);$skip(32); 
  
  println(randomDataList(0));$skip(34); 
  
  println(randomDataList.head);$skip(83); 
  
  val reverseRandomDataList = randomDataList.map( (x: String) => { x.reverse} );System.out.println("""reverseRandomDataList  : List[String] = """ + $show(reverseRandomDataList ));$skip(91); 
                                                  
  val numbersList = List(1,2,3,4,5,6,7);System.out.println("""numbersList  : List[Int] = """ + $show(numbersList ));$skip(61); 
  
  val sumList = numbersList.reduce((a:Int,b:Int) => a+b);System.out.println("""sumList  : Int = """ + $show(sumList ));$skip(103); ;
                                                  
  val noFives = numbersList.filter ( (x) => x!= 5);System.out.println("""noFives  : List[Int] = """ + $show(noFives ));$skip(92); ;
                                                  
  val sumLists = noFives ++ numbersList;System.out.println("""sumLists  : List[Int] = """ + $show(sumLists ));$skip(40); ;
  
  val soretedList = sumLists.sorted;;System.out.println("""soretedList  : List[Int] = """ + $show(soretedList ))}
  
  
}
