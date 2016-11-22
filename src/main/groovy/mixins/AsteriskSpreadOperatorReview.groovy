package mixins

//Reviewing the <b>spread operator</b>
listA = [1, 2, 3, 4]
listB = [5, 6, 7, 8]
otherList = [*listA, *listB]//it "explodes" a list
otherList.each {println "$it"}

listOfNames = ["joe", "jim", "john", "jane", "jill"]
println "${listOfNames*.toUpperCase()}"//it calls a method on each item in the list