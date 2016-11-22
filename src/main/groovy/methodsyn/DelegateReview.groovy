package methodsyn

class Something {
    def delegateTest = {println "delegate is $delegate"}
    def thisTest = {println "this is $this"}
    def ownerTest = {println "owner is $owner"}
}
class SomethingElse extends Something {}

something = new Something()
somethingElse = new SomethingElse()
println "Testing something"
something.delegateTest()
something.thisTest()
something.ownerTest()
println "Testing something else"
somethingElse.delegateTest()
somethingElse.thisTest()
somethingElse.ownerTest()
