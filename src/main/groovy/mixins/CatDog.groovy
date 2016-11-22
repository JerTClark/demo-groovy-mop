package mixins
/**
 * the coolness of Groovy Mixins
 */
class Cat {
    def meow = {println "MEOW"}
}

class Dog {
    def bark = {println "WOOF"}
}

//without annotations, we can...
Dog.mixin(Cat)

Dog doggie = new Dog()
doggie.bark()
doggie.meow()