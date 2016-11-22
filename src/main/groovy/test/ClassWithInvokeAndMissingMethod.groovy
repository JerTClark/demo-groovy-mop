package test

class ClassWithInvokeAndMissingMethod {
    def existingMethod() {"existingMethod"}
    def invokeMethod(String name, args) {"invoke called"}
    def methodMissing(String name, args) {"missing called"}
}