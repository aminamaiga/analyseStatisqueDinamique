package step2;

import org.eclipse.jdt.core.dom.ASTVisitor;

public interface IProcessElement {
  public void accept(ASTVisitor v);

}
