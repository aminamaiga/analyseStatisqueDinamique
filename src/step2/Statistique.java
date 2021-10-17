package step2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import comparators.ClassLineCompare;
import comparators.ClassMethodsCompare;

public class Statistique {
	
	public static final String projectPath = "C:\\Users\\verom\\eclipse-workspace\\currency-converter-in-java-master";
	public static final String projectSourcePath = projectPath + "\\src";
	public static final String jrePath = "C:\\Program Files\\Java\\jdk1.8.0_301\\jre\\lib\\rt.jar";

	// 1. Nombre de classes de l’application.
	public static int getNbClasses(CompilationUnit parse, ArrayList<File> javaFiles) throws IOException {
		DeclarationClassVisitor visitor1 = new DeclarationClassVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor1);
		}
		return visitor1.getClassesNumber().size();

	}

	// 2. Nombre de lignes de code de l’application.
	public static int getNbLines(ArrayList<File> javaFiles) throws IOException {
		int nbOfLines = 0;

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			nbOfLines += (int) content.lines().count();
		}
		return nbOfLines;

	}

	// 3. Nombre total de méthodes de l’application.
	public static int getNbMethods(CompilationUnit parse, ArrayList<File> javaFiles) throws IOException {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor);
		}
		return visitor.getMethods().size();

	}

	// 4. Nombre total de packages de l’application.
	public static int getNbPackages(CompilationUnit parse, ArrayList<File> javaFiles) throws IOException {
		PackageDeclarationVisitor visitor = new PackageDeclarationVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor);
		}
		return visitor.getPackageNumber();

	}

	// 5. Nombre moyen de méthodes par classe.
	public static int getNbrMoyenMethodeParClasse(CompilationUnit parse, ArrayList<File> javaFiles)
			throws IOException {
		return (int) (getNbMethods(parse, javaFiles) / getNbClasses(parse, javaFiles));
	}

	// 6. Nombre moyen de lignes de code par méthode.
	public static int getNbrMoyenLigneParMethode(CompilationUnit parse, ArrayList<File> javaFiles) throws IOException {
		return (int) (getNbLines(javaFiles) / getNbMethods(parse, javaFiles));
	}

	// 7. Nombre moyen d’attributs par classe.
	public static int getNbrAttributMoyenParClasse(CompilationUnit parse, ArrayList<File> javaFiles)
			throws IOException {
		return (int) (getNbAttribut(parse, javaFiles) / getNbClasses(parse, javaFiles));
	}

	// 8. Les 10% des classes qui possèdent le plus grand nombre de méthodes.
	public static List<String> get10ClassesAvecPlusdeMethodes(CompilationUnit parse, ArrayList<File> javaFiles)
			throws IOException {
		DeclarationClassVisitor visitor1 = new DeclarationClassVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor1);
		}
		return visitor1.getClassesNumber().stream().sorted(new ClassMethodsCompare())
				.limit((10 * getNbMethods(parse, javaFiles)) / 100).map(x -> x.getName().toString())
				.collect(Collectors.toList());
	}

	// 9. Les 10% des classes qui possèdent le plus grand nombre d’attributs
	public static List<String> get10ClassesAvecPlusdeAttribut(CompilationUnit parse, ArrayList<File> javaFiles)
			throws IOException {
		DeclarationClassVisitor visitor1 = new DeclarationClassVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor1);
		}
		return visitor1.getClassesNumber().stream().sorted(new ClassLineCompare())
				.limit((10 * getNbMethods(parse, javaFiles)) / 100).map(x -> x.getName().toString())
				.collect(Collectors.toList());
	}

	// 10. Les classes qui font partie en même temps des deux catégories précédentes
	public static List<String> get10ClassesAvecPlusdeMethodesEtAttribut(CompilationUnit parse,
			ArrayList<File> javaFiles) throws IOException {
		List<String> l = new ArrayList<>();
		for (String string : get10ClassesAvecPlusdeMethodes(parse, javaFiles)) {
			for (String string2 : get10ClassesAvecPlusdeAttribut(parse, javaFiles)) {
				if (string.equals(string2)) {
					l.add(string);
				}
			}
		}
		return l.stream().collect(Collectors.toList());
	}

	// 11. Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).
	public static List<String> getClassesAvecPlusdeXMethodes(CompilationUnit parse, ArrayList<File> javaFiles, int X)
			throws IOException {
		DeclarationClassVisitor visitor1 = new DeclarationClassVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor1);
		}
		return visitor1.getClassesNumber().stream().filter(c -> c.getMethods().length > X)
				.map(x -> x.getName().toString()).collect(Collectors.toList());
	}

	// nombre d'attribut total
	public static int getNbAttribut(CompilationUnit parse, ArrayList<File> javaFiles) throws IOException {
		FieldAccessVisitor visitor = new FieldAccessVisitor();

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = parse(content.toCharArray());
			parse.accept(visitor);
		}
		return visitor.getFields().size();

	}
	
	// create AST
		public static CompilationUnit parse(char[] classSource) {
			ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
			parser.setResolveBindings(true);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);

			parser.setBindingsRecovery(true);

			Map options = JavaCore.getOptions();
			parser.setCompilerOptions(options);

			parser.setUnitName("");

			String[] sources = { projectSourcePath };
			String[] classpath = { jrePath };

			parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);
			parser.setSource(classSource);

			return (CompilationUnit) parser.createAST(null); // create and parse
		}

}
