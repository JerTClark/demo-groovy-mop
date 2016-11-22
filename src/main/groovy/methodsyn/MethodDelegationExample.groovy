package methodsyn

class Worker {
    def simpleWork1(spec) {println "Worker does work1 with spec $spec"}
    def simpleWork2() {println "Worker does work2"}
}

class Expert {
    def advancedWork1(spec) {println "Expert does work1 with spec $spec"}
    def advancedWork2(scope, spec) {
        println "Expert does work2 with scope $scope and spec $spec"
    }
}

class Manager {
    def worker = new Worker()
    def expert = new Expert()
    def schedule() {println "Scheduling..."}
    def methodMissing(String name, args) {
        println "Intercepting call to $name"
        def delegateTo = null
        if(name.startsWith("simple")) {delegateTo = worker}
        if(name.startsWith("advanced")) {delegateTo = expert}
        if(delegateTo?.metaClass.respondsTo(delegateTo, name, args)) {
            Manager instance = this
            instance.metaClass."${name}" = {Object[] varargs ->
                delegateTo.invokeMethod(name, varargs)
            }
            delegateTo.invokeMethod(name, args)
        } else {
            throw new MissingMethodException(name, Manager.class, args)
        }
    }
}

jacob = new Manager()
jacob.schedule()
jacob.simpleWork1("fast")
jacob.simpleWork1("quality")
jacob.simpleWork2()
jacob.simpleWork2()
jacob.advancedWork1("fast")
jacob.advancedWork1("quality")
jacob.advancedWork2("prototype", "fast")
jacob.advancedWork2("prototype", "quality")
try {
    jacob.busyWork()
} catch (Exception e) {
    println e
}