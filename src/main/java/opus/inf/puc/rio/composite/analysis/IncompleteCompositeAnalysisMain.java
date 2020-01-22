package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.IncompleteCompositeDTO;
import inf.puc.rio.opus.composite.model.Refactoring;

public class IncompleteCompositeAnalysisMain {

	public static void main(String[] args) {
		IncompleteCompositeAnalysisMain analyzer = new IncompleteCompositeAnalysisMain();
		//analyzer.collectIncompleteComposites();
		analyzer.collectMostCommonIncompleteComposites();
		

	}
	
	protected void collectIncompleteComposites() {
		
		ObjectMapper mapper = new ObjectMapper();
		

		try {

			CompositeRefactoring[] composites = mapper.readValue(new File("presto-compositesRangeBased.json"),
					CompositeRefactoring[].class);

			List<CompositeRefactoring> compositeList = Arrays.asList(composites);

			List<CompositeRefactoring> incompleteCompositeList = getIncompleteComposites(compositeList);

			//mapper.writeValue(new File("incomplete-presto-commmit-based.json"), incompleteCompositeList);
			mapper.writeValue(new File("incomplete-composite-presto-range-based.json"), incompleteCompositeList);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void collectMostCommonIncompleteComposites() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			IncompleteCompositeDTO[] incompleteComposites = mapper.
					readValue(new File("incomplete-composites-dto/incomplete-composite-dto-test.json"),
					IncompleteCompositeDTO[].class);

			List<IncompleteCompositeDTO> incompleteCompositeList = Arrays.asList(incompleteComposites);

			System.out.println(incompleteCompositeList.get(1).getRefactorings().size());
			
			RefactoringPermutation permutation  = new RefactoringPermutation();

			System.out.println(incompleteCompositeList.get(1).toString());
			
			permutation.performPermutations(incompleteCompositeList.get(1).getRefactorings());
			
			List<ArrayList<Refactoring>> permutationsList = permutation.getRefactoringSequencePermutations();
			
		    permutationsList.forEach( permutations -> {
		       	
		    	System.out.println(permutations.toString());
		    	System.out.println();
		    });			
		    
		    java.util.Map<String, Long> rankingRefactoringTypesMapAsComposite = permutationsList
					.stream().collect(
							Collectors.groupingBy(permutations -> permutations.toString(),
									Collectors.counting()));
			
			rankingRefactoringTypesMapAsComposite.entrySet().forEach( incomplete -> {
				System.out.println(incomplete.getKey() + ":" + incomplete.getValue());
			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<CompositeRefactoring> getIncompleteComposites(List<CompositeRefactoring> composites) {

		List<CompositeRefactoring> incompleteCompositeList = new ArrayList<CompositeRefactoring>();

		for (CompositeRefactoring composite : composites) {
			String refactoringTypes = getRefactoringAsText(composite.getRefactorings());
			
			if (refactoringTypes.contains("Extract Method") || refactoringTypes.contains("Move Method")
					|| refactoringTypes.contains("Pull Up Method")) {

				incompleteCompositeList.add(composite);
                System.out.println(composite.getId());
			
			}
		}
        
		return incompleteCompositeList;

	}

	private String getRefactoringAsText(List<Refactoring> refactorings) {

		List<String> refactoringTypesList = new ArrayList<String>();
		
		for (Refactoring refactoring : refactorings) {

			refactoringTypesList.add(refactoring.getRefactoringType());

		}
		
		String refactoringTypesAsText = String.join(",", refactoringTypesList);
		System.out.println(refactoringTypesAsText);
		return refactoringTypesAsText;

	}

}
