package synthesis

class BigDog implements GroovyInterceptable {
    def int num = 0
    BigDog(){}
    BigDog(int num) {
        this.num = num
    }
    def bark(int num) {
        StringBuffer stringBuffer = new StringBuffer()
        1.upto(num){  stringBuffer.append "Woof! "}
        stringBuffer
    }
    def invokeMethod(String name, args) {
        System.out.println "$name($args) intercepted"
        def method = this.metaClass.getMetaMethod(name, args)
        if(method) {
            method.invoke(this, args)
        } else {
            //Synthesize a method on this instance
            if(this.num != 0 && name.startsWith("go")) {
                System.out.println "Adding to ExpandoMetaClass of instance"
                def emc = new ExpandoMetaClass(BigDog)
                def synthesizedMethod = {
                    def StringBuffer stringBuffer = new StringBuffer()
                    name.each {
                        def caps = ~"[A-Z]"
                        if(it.matches(caps)) stringBuffer.append " $it"
                        else stringBuffer.append it
                    }
                    "That dog can ${stringBuffer.toString().toLowerCase()}!"
                }
                emc."$name" = synthesizedMethod//cache the method
                emc.initialize()
                this.metaClass = emc
                synthesizedMethod(args)
            } else {
                throw new MissingMethodException(name, BigDog.class, args)
            }
        }
    }
}

def bigDog = new BigDog(1)
println bigDog.bark(3)
println bigDog.goMeow()
println bigDog.goMeow()
try {
    println bigDog.poop()
} catch (Exception e) {
    println "OH NO! ${e.getMessage()}"
}
def bigDogToo = new BigDog()
try {
    println bigDogToo.goMoo()
} catch (Exception e) {
    println "WHAT THE?! ${e.getMessage()}"
}