import test.AGroovyObject
import test.AnInterceptable
import test.ClassWithInvokeAndMissingMethod
import test.ClassWithInvokeOnly

class TestMethodInvocation extends GroovyTestCase{

    void testInterceptedMethodCallOnPOJO() {
        def value = new Integer(3)
        Integer.metaClass.toString = {-> "intercepted"}
        assertEquals "intercepted", value.toString()
    }

    void testInterceptableCalled() {
        def object = new AnInterceptable()
        assertEquals "intercepted", object.existingMethod()
        assertEquals "intercepted", object.nonExistingMethod()
    }

    void testUnInterceptedExistingMethodCalled() {
        def object = new AGroovyObject()
        assertEquals "existingMethod", object.existingMethod()
    }

    void testPropertyThatIsClosureCalled() {
        def object = new AGroovyObject()
        assertEquals "closure called", object.closureProp()
    }

    void testMethodMissingCalledOnlyForNonExistent() {
        def object = new ClassWithInvokeAndMissingMethod()
        assertEquals "existingMethod", object.existingMethod()
        assertEquals "missing called", object.nonExistingMethod()
    }

    void testInvokeMethodCalledForOnlyNonExistent() {
        def object = new ClassWithInvokeOnly()
        assertEquals "existingMethod", object.existingMethod()
        assertEquals "invoke called", object.nonExistingMethod()
    }

}