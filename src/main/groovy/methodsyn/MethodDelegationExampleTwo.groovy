package methodsyn

Object.metaClass.delegateCallsTo = { Class... classOfDelegates ->
    def objectOfDelegates = classOfDelegates.collect {it.newInstance()}
    delegate.metaClass.methodMissing = {String name, args ->
        println "Intercepting call to $name..."
        def delegateTo = objectOfDelegates.find {
            it.metaClass.respondsTo(it, name, args)
        }
        if(delegateTo) {
            delegate.metaClass."${name}" = {Object[] varargs ->
                delegateTo.invokeMethod(name, varargs)
            }
        } else {
            throw new MissingMethodException(name, delegate.getClass(), args)
        }
    }
}

class ManagerTwo {
    {delegateCallsTo Worker, Expert, GregorianCalendar}
    def schedule() {println "Scheduling..."}
}

jacob = new ManagerTwo()
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