package mixins

class Friend {
    //name is not defined, the mixed in class will provide it
    def listen = {println "$name is listening as a friend" }
}
@Mixin(Friend)
class Person {
    String firstName
    String lastName
    String getName() {"$firstName $lastName"}
}
def jimmyJoe = new Person(firstName: "Jimmy", lastName: "John")
jimmyJoe.listen()