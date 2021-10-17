package step2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class DeclarationClassVisitor extends ASTVisitor {
	List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();
	
	//nombre total de classe
	public boolean visit(TypeDeclaration node) {
		if(!node.isInterface())
			classes.add(node);
		return super.visit(node);
	}
	
	//nombre total de classe
	public List<TypeDeclaration> getClassesNumber(){
		return classes;
	}

}
