package categories

//we will specify this as a category in a use block and intercept all calls to toString(String arg)
class InterceptCategory {
    def static toString(String self) {
        def method = self.metaClass.methods.find {it.name == "toString"}
        "!!" + method.invoke(self, null) + "!!"
    }
}

//using the annotation, we will intercept all calls to Integer's toString()
@Category(Integer)
class InterceptIntegerCategory {
    def toString() {
        def method = this.metaClass.methods.find {method -> method.name == "toString"}
        "!!${method.invoke(this, null)}!!"
    }
}

@Category(Object)
class InterceptObjectCategory {
    def toString() {
        def method = this.metaClass.methods.find {method -> method.name == "toString"}
        "!!${method.invoke(this, null)}!!"
    }
}

//won't see a difference here
println "1234".toString()

//intercept to toString(String arg)
use(InterceptCategory) {
    println "String parameter use block"
    println "1234".toString()
    println 1234.toString()//no difference
}
use(InterceptIntegerCategory) {
    println "Integer use block"
    println "1234".toString()//no difference
    println 1234.toString()
}
use(InterceptObjectCategory) {
    println "Object use block"
    println "1234".toString()
    println 1234.toString()
    println new Object(){int i = 1234;}.i.toString()//weirdness
}