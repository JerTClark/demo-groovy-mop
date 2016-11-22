package meta

import groovy.transform.Immutable

import java.lang.ref.PhantomReference

def printMetaClassInfo(instance) {
    println "MetaClass of $instance is ${instance.metaClass.class.simpleName}"
    println "with a delegate of ${instance.metaClass.delegate.class.simpleName}"
}

printMetaClassInfo 2
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName}"
println "Adding a method to Integer metaClass..."
Integer.metaClass.myNewMethod = {->/**/}

printMetaClassInfo 2
println "MetaClass of Integer is now ${Integer.metaClass.class.simpleName}"

println ""
println ""

@Immutable
class MyClass {
    String name
}

objectOne = new MyClass("objectOne")
printMetaClassInfo objectOne
println "Adding a method to ${objectOne.class.simpleName}..."
MyClass.metaClass.aNewMethod = {-> /**/}
printMetaClassInfo objectOne

println "objectTwo being created after"
objectTwo = new MyClass("objectTwo")
printMetaClassInfo objectTwo