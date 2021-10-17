package step2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {


	public static void main(String[] args) throws IOException {

		// read java files
		final File folder = new File(Statistique.projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);
		CompilationUnit parse = null;

		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);
			parse = Statistique.parse(content.toCharArray());
		}

		System.out.println("Le nombre total de classes est : " + Statistique.getNbClasses(parse, javaFiles));
		System.out.println("Le nombre total de lignes est : " + Statistique.getNbLines(javaFiles));
		System.out.println("Le nombre total de methodes est : " + Statistique.getNbMethods(parse, javaFiles));
		System.out.println("Le nombre total de packages est : " + Statistique.getNbPackages(parse, javaFiles));
		System.out.println("Le nombre moyen de méthode par classes : " + Statistique.getNbrMoyenMethodeParClasse(parse, javaFiles));
		System.out.println("Le nombre total de classes est : " + Statistique.getNbClasses(parse, javaFiles));
		System.out.println(
				"Le nombre moyen de lignes de codes par méthode est : " + Statistique.getNbrMoyenLigneParMethode(parse, javaFiles));
		System.out
				.println("Le nombre d'attribut moyen par classes : " + Statistique.getNbrAttributMoyenParClasse(parse, javaFiles));
		System.out.println("Les 10%	des classes qui possèdent le plus grand	nombre de méthodes : "
				+ Statistique.get10ClassesAvecPlusdeMethodes(parse, javaFiles));
		System.out.println("Les 10%	des classes qui possèdent le plus grand	nombre d'attributs : "
				+ Statistique.get10ClassesAvecPlusdeAttribut(parse, javaFiles));
		System.out.println("Les classes qui font partie en même temps des deux catégories précédentes: "
				+ Statistique.get10ClassesAvecPlusdeMethodesEtAttribut(parse, javaFiles));
		System.out.println("Les classes qui possèdent plus de X méthodes (la valeur de X est donnée).: "
				+ Statistique.getClassesAvecPlusdeXMethodes(parse, javaFiles, 3));
	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

}
