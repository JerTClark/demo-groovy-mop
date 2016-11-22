package compiletime

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class EAMTransformation implements ASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        astNodes.findAll {node ->
            node instanceof ClassNode
        }
        .each { classNode ->

            /*For each class node in the AST, we're inserting this closure's code.*/
            def useMethodBody = new AstBuilder().buildFromCode {
                def instance = newInstance()
                try {
                    instance.open()
                    instance.with block
                } finally {
                    instance.close()
                }
            }

            /*Add the AST node we created to a new MethodNode.*/
            //noinspection GroovyAssignabilityCheck
            def useMethod = new MethodNode(
                    "use", MethodNode.ACC_PUBLIC | MethodNode.ACC_STATIC, ClassHelper.OBJECT_TYPE,
                    [new Parameter(ClassHelper.OBJECT_TYPE, "block")] as Parameter[],
                    [] as ClassNode[], useMethodBody[0])

            /*Add the method node to the class node.*/
            classNode.addMethod(useMethod)

        }
    }
}
