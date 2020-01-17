package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;

public class IncompleteCompositeAnalysisMain {

	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();
		IncompleteCompositeAnalysisMain analyzer = new IncompleteCompositeAnalysisMain();

		try {

			CompositeRefactoring[] composites = mapper.readValue(new File("presto-compositesRangeBased.json"),
					CompositeRefactoring[].class);

			List<CompositeRefactoring> compositeList = Arrays.asList(composites);

			List<CompositeRefactoring> incompleteCompositeList = analyzer.getIncompleteComposites(compositeList);

			//mapper.writeValue(new File("incomplete-presto-commmit-based.json"), incompleteCompositeList);
			mapper.writeValue(new File("incomplete-composite-presto-range-based.json"), incompleteCompositeList);

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
