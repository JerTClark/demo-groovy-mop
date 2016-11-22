package compiletimeintercept

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import java.beans.Statement

/**
 * To be honest. This one just got weird on me. See the "InjectIsDead"
 * and "MonsterFight" for a better, more gooder note-takier example I did.
 */
@GroovyASTTransformation(phase = CompilePhase.CONVERSION)
class InjectAudit implements ASTTransformation {
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        ClassNode checkingAccountClassNode =
                sourceUnit.AST?.classes?.find {it.name == "compiletimeintercept.CheckingAccount"}
        injectAuditMethod(checkingAccountClassNode)
    }

    static void injectAuditMethod(ClassNode checkingAccountClassNode) {
        List<MethodNode> nonAuditMethods =
                checkingAccountClassNode?.methods?.findAll {
                    it.name != "audit"
                }
        nonAuditMethods?.each {injectMethodWithAudit(it)}
    }

    static void injectMethodWithAudit(MethodNode methodNode) {
        ExpressionStatement callToAudit = new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression('this'),
                        'audit',
                        new ArgumentListExpression(methodNode.parameters)
                )
        )
        methodNode.code.statements.add(0, callToAudit)
    }
}