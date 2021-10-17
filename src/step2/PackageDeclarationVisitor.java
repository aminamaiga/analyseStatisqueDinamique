package step2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;


public class PackageDeclarationVisitor extends ASTVisitor {
	List<PackageDeclaration> classes = new ArrayList<PackageDeclaration>();
	
	//nombre total de package
	public boolean visit(PackageDeclaration node) {
			classes.add(node);
		return super.visit(node);
	}
	
	//nombre total de package
	public int getPackageNumber(){
		return classes.size();
	}

}
