package mixins
//Adding the invokeOnPreviousMixin method to the Object MetaClass
Object.metaClass.invokeOnPreviousMixin = {
    MetaClass currentMixinMetaClass, String methodName, Object[] args ->
        def previousMixin = delegate.getClass()
        for(mixin in mixedIn.mixinClasses) {
            if(mixin.mixinClass.theClass ==
            currentMixinMetaClass.delegate.theClass) break
            previousMixin = mixin.mixinClass.theClass
        }
        mixedIn[previousMixin]."$methodName"(*args)
}

class JeremyWriter {
    StringBuilder stringBuilder = new StringBuilder()
    void write(String message) {
        stringBuilder.append(message)
    }
    String toString() {
        stringBuilder.toString()
    }
}
def writeSomething(myWriter, message) {
    myWriter.write(message)
    println myWriter//implicitly calls toString()
}
def getMixedInWriter(jeremyWriterClass, Object[] myMixins = []) {
    def refreshWriter = jeremyWriterClass.newInstance()
    myMixins.each {myMixin -> refreshWriter.metaClass.mixin myMixin}
    refreshWriter
}
//use the basic class
writeSomething(getMixedInWriter(JeremyWriter), "This is my message")
println ""

//include some mixin stuff
class CapsMessage {
    void write(String message) {
        def allUpper = message.toUpperCase()
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}

class EditMessage {
    void write(String message) {
        def edited = message.replaceAll("This", "That")
        edited = edited.replaceAll("is", "was")
        invokeOnPreviousMixin(metaClass, "write", edited)
    }
}

writeSomething(getMixedInWriter(JeremyWriter, CapsMessage, EditMessage), "This is my message")