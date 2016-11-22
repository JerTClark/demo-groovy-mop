package compiletime

/**
 * The specified implementation of GroovyASTTransformation we gave in the
 * EAM annotaion source ("compiletime.EAMTransformation") executes here
 * upon our annotation being found in the source.
 */
@EAM
class Resource {
    private def open(){println "Opened..."}
    private def close(){println "Closed..."}
    def read() {println "Reading"}
    def write() {println "Writing"}
}

println "Using Resource"
Resource.use {
    read()
    write()
}
//nice!