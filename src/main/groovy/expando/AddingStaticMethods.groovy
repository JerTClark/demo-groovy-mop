package expando

Number.metaClass."static".isEven = {value -> value % 2 == 0}
println Number.isEven(100)