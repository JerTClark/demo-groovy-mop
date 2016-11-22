package instances

class Person {
    def play() {println "playing"}
}
def emc = new ExpandoMetaClass(Person)
emc.sing = {-> println "La la la la"}
emc.laugh = {-> println "Ha ha ha ha"}
emc.initialize()
def guy = new Person()
def dude = new Person()
dude.metaClass = emc
guy.metaClass {
    laugh = {-> println "Ha ha ha ha!"}
    sing = {-> println "La la la la"}
}
guy.sing()
guy.laugh()
guy.play()
dude.sing()
dude.laugh()
dude.play()