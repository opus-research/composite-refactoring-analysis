package inf.puc.rio.opus.composite.analysis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;
import inf.puc.rio.opus.composite.model.effect.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import org.paukov.combinatorics3.Generator;

public class CompositeUtils {



	public static void countComposites(List<String> projects){

		ObjectMapper mapper = new ObjectMapper();
		CsvWriter csv = new CsvWriter("general-data-new-projects.csv", ',', Charset.forName("ISO-8859-1"));
		try {
			csv.write("Project");
			csv.write("Refactorings");
			csv.write("Composites");

			csv.endRecord();
			for (String project : projects) {
				Refactoring[] refactorings = mapper.readValue(new File("data/refactorings/" + project + "-refactorings.json"), Refactoring[].class);

				CompositeRefactoring[] composites = mapper.readValue(new File("data/composites/" + project + "-composite-rangebased.json"), CompositeRefactoring[].class);

				csv.write(project);
				csv.write(String.valueOf(refactorings.length));
				csv.write(String.valueOf(composites.length));

				csv.endRecord();

			}

			csv.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List<String> convertRefactoringsTextToRefactoringsList(String refactoringsText){
		
		List<String> refactorings = new ArrayList<String>();
		
		refactoringsText = refactoringsText.replace("[", "");
		refactoringsText = refactoringsText.replace("]", "");
		
		refactorings = Arrays.asList(refactoringsText.split("\\s*,\\s*"));
		
		return refactorings;
		
	}
	
	
	public static List<String> convertTextToList(String textList){
		List<String> myList = new ArrayList<String>(Arrays.asList(textList.split(",")));
		return myList;
	}

	public static void countElementsFromTextList(String textList){
		List<String> myList = new ArrayList<String>(Arrays.asList(textList.split(",")));
		System.out.println(myList.size());
	}

	public static String getSmellBeforeDetails(List<CodeSmellDTO> codeSmells) {

		String smellDetails = "";

		for (CodeSmellDTO codeSmell : codeSmells) {
			smellDetails += codeSmell.getType() + ":" + codeSmell.getBeforeComposite() + "\n";
		}
		return smellDetails;
	}

	public static String getRefactoringDetails(List<Refactoring> refactorings) {

		String details = "";
		for (Refactoring refactoring : refactorings) {
			details += refactoring.getRefactoringDetail() + "\n";
		}
		return details;
	}

	public static String getSmellAfterDetails(List<CodeSmellDTO> codeSmells) {
		String smellDetails = "";

		for (CodeSmellDTO codeSmell : codeSmells) {
			smellDetails += codeSmell.getType() + ":" + codeSmell.getAfterComposite() + "\n";
		}
		return smellDetails;
	}

	public List<CompositeRefactoring> getComposites(String pathComposites){
		ObjectMapper mapper = new ObjectMapper();
		List<CompositeRefactoring> compositeList = new ArrayList<>();
		try {

			CompositeRefactoring[] composites = mapper.readValue(new File(pathComposites),
					CompositeRefactoring[].class);

			compositeList = Arrays.asList(composites);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compositeList;
	}

	public static CompositeDTO setCompositeDTO(CompositeRefactoring compositeRefactoring){
		CompositeDTO compositeDTO = new CompositeDTO();
		compositeDTO.setId(compositeRefactoring.getId());

		return compositeDTO;
	}

	public List<CompositeRefactoring> filterCompositesByIds(String pathComposites, String ids){
		List<String> idsList = new ArrayList<String>(Arrays.asList(ids.split(",")));

		List<CompositeRefactoring> allComposites = new ArrayList<>();
		allComposites = getComposites(pathComposites);

		List<CompositeRefactoring> filteredComposites = new ArrayList<>();

		for (String id : idsList) {
			for (CompositeRefactoring composite : allComposites) {
				if(composite.getId().equals(id)){
					filteredComposites.add(composite);
				}

			}
		}

		return filteredComposites;
	}


	public static List<String> generateCombinations(Collection<String> collection, int r){
		List<Object> combinations =  Generator.combination(collection)
				.simple(r)
				.stream().collect(Collectors.toList());

		List<String> combinationList = combinations.stream()
				.map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());

	   	System.out.println(combinationList);
		return combinationList;
	}

}
