package methodsyn

vehicleA = new Expando()
vehicleB = new Expando(type: "car", engine: "v6", fuel: "gas", drive: {println "vroom-vroom"})
vehicleA.type = "truck"
vehicleA.engine = "v8"
vehicleA.fuel = "diesel"
vehicleA.drive = {println "VROOM VROOM"}

println "vehicleA: $vehicleA"
println "vehicleB: $vehicleB"
println "${vehicleB.getProperty("type")}"

vehicleA.drive()
vehicleB.drive()