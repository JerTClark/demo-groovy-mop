package compiletimeintercept

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * Groovy enables access to the abstract tree syntax during compile time.
 * <p>Nine phases of the Groovy Compiler exist and are each accessible via the annotation.
 * <ol>
 *     <li>initialization</li>
 *     <li><b>parsing</b> (the concrete syntax tree is created; not much to work with here but grammar via the AntlrParserPlugin...)</li>
 *     <li><b>conversion</b> (scripts become classes here; AST is created; earliest phase to hook into, but only global AST transformations here; the AST is still runtime agnostic-- not JVM yet; this is where Grooscript hooks in to create JavaScript from Groovy code-- visitor pattern happens here-- <b>best hook in location</b>)</li>
 *     <li><b>semantic analysis</b> (computationally intensive phase, esp. when dynamic typing used; resolves things like imports, variable scopes; local AST transformations can only be hooked after this-- the AST transformations are collected here, making this acceptable phase for all hooks into AST transformations-- <b>recommended hook in location</b>)</li>
 *     <li>canonicalization (usually the last phase to hook into the AST transformation-- hooks into later phases, "all bets are off"; traits are woven here; completes the AST of inner classes here)</li>
 *     <li>instruction selection (all AST operations that need to be done just before bytecode generation, optional type checking; optional static compilation)</li>
 *     <li><b>class generation (converts an AST into bytecode using the visitor pattern; uses the ASM library)</b></li>
 *     <li>output (optionally write the bytecode to a file; optionally just load the classes using a class loader)</li>
 *     <li>finalization (not in use)</li>
 * </ol>
 * <p>Keep in mind that only a concrete syntax tree (CST) exists prior to the conversion phase in which
 * the AST is actually created and there is syntax available with which the developer is able to work.
 * <p>There are two categories of ASTNodes in Groovy: statements (IfStatement, BlockStatement...) and
 * expressions (ConstantExpression, MethodCallExpression...). Expressions have a value whereas statements
 * do not.
 * <p>The best way to learn and understand the AST in Groovy is to use the <b>groovyConsole</b> and use
 * CTRL + T to show the AST Browser. Here, the AST is displayed in a very familiar file tree hierarchy that
 * can be easily navigated and explored live while writing Groovy in the console.
 * <p>Using the GroovyASTTransformation#phase method, we indicate the point at which our
 * AST transformation must be applied.
 */
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class InjectIsDead implements ASTTransformation {

    /**
     * A CompilationUnit is responsible for the compile phases lifecycle. It owns
     * a set of SourceUnits... the CompileUnit collects all ASTs of a single compilation
     * unit... each SourceUnit is processed phase-by-phase.
     */

    /**
     * This method will be called once the compiler reaches the indicated phase.
     * The SourceUnit represents the code that is currently in the process of being
     * compiled-- it is a single source file or script.
     * <p>The ASTNodes are representations of the nodes on the AST.
     */
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        /**
         * Here we can do something like exploring the classes in
         * the SourceUnit's AST classes to find a particular class...
         */
        ClassNode classNode = sourceUnit.AST?.classes?.find {
            it.name == "compiletimeintercept.Monster"
        }
        if (classNode != null) findIsDeadMethod(classNode)
    }

    static void findIsDeadMethod(ClassNode classNode) {
        MethodNode getsHitMethod = classNode.methods?.find {
            it.name == "getsHit"
        }
        if (getsHitMethod != null) injectWithIsDead(getsHitMethod)
    }

    static void injectWithIsDead(MethodNode methodNode) {
        ExpressionStatement expressionStatement = new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        "isDead",
                        new ArgumentListExpression()
                )
        )
        methodNode.code.statements.add(expressionStatement)
    }

}