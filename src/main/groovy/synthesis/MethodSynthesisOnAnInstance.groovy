package synthesis

class PuppyDog {}

def emc = new ExpandoMetaClass(PuppyDog)
emc.methodMissing = {String name, args ->
    def StringBuffer stringBuffer = new StringBuffer()
    name.each {
        def caps = ~"[A-Z]"
        if(it.matches(caps)) stringBuffer.append " $it"
        else stringBuffer.append it
    }
    "The puppy dog can ${stringBuffer.toString().toLowerCase()}!"
}
emc.initialize()

def puppy = new PuppyDog()
puppy.metaClass = emc
println puppy.poopOnTheCarpet()
println puppy.goPottyOutside()
println puppy.goToTheStoreAndBuyGroceries()