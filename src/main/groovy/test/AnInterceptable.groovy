package test

class AnInterceptable implements GroovyInterceptable {
    def existingMethod() {}
    def invokeMethod(String name, args) {"intercepted"}
}