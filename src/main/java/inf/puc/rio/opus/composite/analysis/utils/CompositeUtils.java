package inf.puc.rio.opus.composite.analysis.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import inf.puc.rio.opus.composite.model.refactoring.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.refactoring.Refactoring;
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

		StringBuilder smellDetails = new StringBuilder();

		for (CodeSmellDTO codeSmell : codeSmells) {
			smellDetails.append(codeSmell.getType())
					    .append(":").append(codeSmell.getBeforeComposite())
					    .append("\n");
		}
		return smellDetails.toString();
	}

	public static String getRefactoringDetails(List<Refactoring> refactorings) {

		StringBuilder details = new StringBuilder();
		for (Refactoring refactoring : refactorings) {

			details.append("ID: ")
					.append(refactoring.getRefactoringId())
					.append(" Details: ")
					.append(refactoring.getRefactoringDetail())
					.append("\n");
		}
		return details.toString();
	}

	public static String getSmellAfterDetails(List<CodeSmellDTO> codeSmells) {
		StringBuilder smellDetails = new StringBuilder();

		for (CodeSmellDTO codeSmell : codeSmells) {
			    smellDetails
					.append(codeSmell.getType())
					.append(":")
					.append(codeSmell.getAfterComposite())
					.append("\n");
		}
		return smellDetails.toString();
	}

	public static boolean isAcceptableComposite(CompositeDTO compositeDTO){

		if(compositeDTO.getRefactoringsList() !=null && compositeDTO.getRefactoringsList().size() <= 7) {
			int countSameOrderCommit = 0;
			int orderCommit = compositeDTO.getRefactoringsList().get(0).getCurrentCommit().getOrderCommit();
			for (Refactoring refactoring : compositeDTO.getRefactoringsList()) {
				if (refactoring.getCurrentCommit().getOrderCommit() == orderCommit) {
					countSameOrderCommit++;
				}
			}
			return countSameOrderCommit == compositeDTO.getRefactoringsList().size();
		}
		return false;
	}

	public static String getRefactoringInfoAboutCommit(List<Refactoring> refactorings) {
		StringBuilder commitInfo = new StringBuilder();
		for (Refactoring refactoring : refactorings) {

			commitInfo.append("Order Commit:")
					.append(refactoring.getCurrentCommit().getOrderCommit().toString())
					.append(" Previous commit: ")
					.append(refactoring.getCurrentCommit().getPreviousCommit())
					.append(" Current Commit: ").append(refactoring.getCurrentCommit().getCommit())
					.append("\n");
		}
		return commitInfo.toString();
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


	public static String converterRefactoringListToText(List<Refactoring> refactorings){

		List<String> refs = new ArrayList<>();

		refactorings.forEach( refactoring -> {
			refs.add(refactoring.getRefactoringType());
		});

		return refs.toString();
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
