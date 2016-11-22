package test

class ClassWithInvokeOnly {
    def existingMethod() {"existingMethod"}
    def invokeMethod(String name, args) {"invoke called"}
}