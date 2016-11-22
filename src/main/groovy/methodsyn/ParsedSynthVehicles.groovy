package methodsyn

data = new File("vehicles.data").readLines()
props = data[0].split(", ")
data -= data[0]
vehicles = data.collect {
    vehicle = new Expando()
    it.split(", ").eachWithIndex { String entry, int i ->
        vehicle[props[i]] = entry
    }
    vehicle
}

props.each {println "$it"}

vehicles.each {println "$it"}