package compiletime

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * With the GroovyASTTransformationClass added to our new annotation here,
 * we specify that the particular implementation of GroovyASTTransformation
 * ("compiletime.EAMTransformation") should execute wherever our annotation
 * is found in the source.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass("compiletime.EAMTransformation")
public @interface EAM {
}