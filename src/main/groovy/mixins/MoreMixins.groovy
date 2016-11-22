package mixins

/**
 * we create a "chain" of mixins by overloading, in a sense, the method we name
 * "write"
 */
//adds an invokeOnPreviousMixin closure to every object via Object's metaclass
Object.metaClass.invokeOnPreviousMixin = {
    MetaClass currentMixinMetaClass, String method, Object[] args ->
        def previousMixin = delegate.getClass()
        for(mixin in mixedIn.mixinClasses) {
            if(mixin.mixinClass.theClass ==
            currentMixinMetaClass.delegate.theClass) break
            previousMixin = mixin.mixinClass.theClass
        }
        mixedIn[previousMixin]."$method"(*args)
}

//simple abstract class
abstract class Writer {
    abstract void write(String message)
}
//overrides the write method of Writer
class StringWriter extends Writer {
    def target = new StringBuilder()
    @Override
    void write(String message) {
        target.append(message)
    }
    //used via implicit conversion of println "" statement
    String toString() {
        target.toString()
    }
}
def writeStuff(writer) {
    writer.write("This is stupid")
    println writer
}
def create(theWriter, Object[] filters = []) {
    def instance = theWriter.newInstance()
    filters.each {filter -> instance.metaClass.mixin filter }
    instance
}
//a filter
class UpperCaseFilter {
    void write(String message) {
        def allUpper = message.toUpperCase()
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}
//a filter
class ProfanityFilter {
    void write(String message) {
        def filtered = message.replaceAll("stupid", "s*****")
        invokeOnPreviousMixin(metaClass, "write", filtered)
    }
}
/**
 * passes the instance from create() as the Writer to writeStuff()
 * StringWriter is the class of the Writer instance
 * UppercaseFilter and ProfanityFilter are passed as the Object[] filters and
 * added to the mixins
 */
writeStuff(create(StringWriter, UpperCaseFilter, ProfanityFilter))