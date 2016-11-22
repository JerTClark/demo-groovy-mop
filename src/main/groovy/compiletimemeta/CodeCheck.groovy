package compiletimemeta

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * For more notes on the GroovyASTTransformation and AST transformation, itself,
 * see the class "compiletimeintercept.InjectIsDead."
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class CodeCheck implements ASTTransformation {
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        sourceUnit.ast.classes.each {classNode ->
            classNode.visitContents(new OurClassVisitor(sourceUnit))
        }

    }
}

/**
 * A very simple Groovy class visitor used to visit every method, etc. during
 * the compile phase indicated by the ASTTransformation's phase parameter (which
 * is the semantic analysis phase above, and for good reason).
 *
 * This example from the book is intended to raise an exception to single-letter
 * parameters and method names (the "code smell" example). This can be seen in the
 * "MorallyWrong" script.
 */
class OurClassVisitor implements GroovyClassVisitor {
    SourceUnit sourceUnit
    OurClassVisitor(theSourceUnit) {sourceUnit = theSourceUnit}

    /**We'll report an error when one of our code smells appears.*/
    private void reportError(message, lineNumber, columnNumber) {
        sourceUnit.addError(new SyntaxException(message, lineNumber, columnNumber))
    }

    /**Here, the visitor visits each method node during each phase of compilation...*/
    @Override
    void visitMethod(MethodNode methodNode) {
        /*Check the method name size*/
        if(methodNode.name.size() == 1) {
            reportError "Make method name descriptive, avoid single letter names",
                    methodNode.lineNumber, methodNode.columnNumber
        }
        /*Check the method parameter names*/
        methodNode.parameters.each {param ->
            if(param.name.size() == 1) {
                reportError "Single letter parameters are just plain wrong!",
                        param.lineNumber, param.columnNumber
            }
        }
    }

    /**not interested in this just now...*/
    @Override
    void visitClass(ClassNode classNode) { }

    /**not interested in this just now...*/
    @Override
    void visitConstructor(ConstructorNode constructorNode) { }

    /**not interested in this just now...*/
    @Override
    void visitField(FieldNode fieldNode) { }

    /**not interested in this just now...*/
    @Override
    void visitProperty(PropertyNode propertyNode) { }
}