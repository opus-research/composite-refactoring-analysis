package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.IncompleteCompositeDTO;
import inf.puc.rio.opus.composite.model.Refactoring;

public class IncompleteCompositeAnalysisMain {

	public static void main(String[] args) {
		IncompleteCompositeAnalysisMain analyzer = new IncompleteCompositeAnalysisMain();
		// analyzer.collectIncompleteComposites();
		// analyzer.collectMostCommonIncompleteComposites();

		List<String> refactoringsA = new ArrayList<String>();
		refactoringsA.add("Extract Method");
		refactoringsA.add("Move Method");
		refactoringsA.add("Rename Method");

		List<String> refactoringsB = new ArrayList<String>();
		refactoringsB.add("Extract Method");
		refactoringsB.add("Extract Method");
		refactoringsB.add("Rename Method");

		List<String> refactoringsC = new ArrayList<String>();
		refactoringsC.add("Extract Method");
		refactoringsC.add("Move Method");
		refactoringsC.add("Rename Method");
		
		
		List<String> refactoringsD = new ArrayList<String>();
		refactoringsD.add("Extract Method");
		refactoringsD.add("Rename Method");
		refactoringsD.add("Extract Method");
	

		List<List<String>> refs = new ArrayList<List<String>>();
		refs.add(refactoringsA);
		refs.add(refactoringsB);
		refs.add(refactoringsC);
		refs.add(refactoringsD);
		
		Map<List<String>, Long> mostCommonComposites = new HashMap<List<String>, Long>();

		for (List<String> refList : refs) {
			
			boolean[] wasFound = {false};
			
			mostCommonComposites.entrySet().forEach(refTextList -> {
				
				if (analyzer.containsAllRefs(refTextList.getKey(), refList)) {
                    
					long quantityRefList = refTextList.getValue();

					quantityRefList = quantityRefList + 1;

					refTextList.setValue(quantityRefList);
					wasFound[0] = true;
					
				}
			});

			if (!wasFound[0]) {

				mostCommonComposites.put(refList, Long.valueOf(1));
				// System.out.println("Add list: " + refList.toString());
			}
		}

		mostCommonComposites.entrySet().forEach(composite -> {
			System.out.println(composite.getKey());
			System.out.println(composite.getValue());
		});

	}

	private boolean containsAllRefs(List<String> refTextList, List<String> refList) {
		// TODO Auto-generated method stub

		List<Integer> equalsRefIIndex = new ArrayList<Integer>();
		List<Integer> equalsRefJIndex = new ArrayList<Integer>();

		for (int i = 0; i < refTextList.size(); i++) {

			for (int j = 0; j < refList.size(); j++) {

				if (refTextList.get(i).equals(refList.get(j)) && !equalsRefIIndex.contains(i)
						&& !equalsRefJIndex.contains(j)) {

					equalsRefIIndex.add(i);
					equalsRefJIndex.add(j);

				}

			}

		}

		return !refList.isEmpty() && (refTextList.size() == equalsRefIIndex.size());
	}

	protected void collectIncompleteComposites() {

		ObjectMapper mapper = new ObjectMapper();

		try {

			CompositeRefactoring[] composites = mapper.readValue(new File("presto-compositesRangeBased.json"),
					CompositeRefactoring[].class);

			List<CompositeRefactoring> compositeList = Arrays.asList(composites);

			List<CompositeRefactoring> incompleteCompositeList = getIncompleteComposites(compositeList);

			// mapper.writeValue(new File("incomplete-presto-commmit-based.json"),
			// incompleteCompositeList);
			mapper.writeValue(new File("incomplete-composite-presto-range-based.json"), incompleteCompositeList);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void collectMostCommonIncompleteComposites() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
					"incomplete-composites-dto/incomplete-composite-couchbase-java-client-commit-based-saida.json"),
					IncompleteCompositeDTO[].class);

			List<IncompleteCompositeDTO> incompleteCompositeList = Arrays.asList(incompleteComposites);

			Map<List<String>, Long> mostCommonIncompleteComposites = new HashMap<List<String>, Long>();

			for (IncompleteCompositeDTO incomplete : incompleteCompositeList) {
				List<String> refactoringTextList = incomplete.getRefactoringsAsTextList();

				mostCommonIncompleteComposites.entrySet().forEach(refTextList -> {

					if (refTextList.getKey().containsAll(refactoringTextList)) {

						long quantityRefList = refTextList.getValue();

						refTextList.setValue(quantityRefList++);

					}
				});

				if (!mostCommonIncompleteComposites.keySet().contains(refactoringTextList)) {

					mostCommonIncompleteComposites.put(refactoringTextList, Long.valueOf(1));
				}

			}

			writeMostCommonIncompleteCompositesRanking(mostCommonIncompleteComposites);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void collectMostCommonIncompleteCompositesPerPermutations() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
					"incomplete-composites-dto/incomplete-composite-couchbase-java-client-commit-based-saida.json"),
					IncompleteCompositeDTO[].class);

			List<IncompleteCompositeDTO> incompleteCompositeList = Arrays.asList(incompleteComposites);

			List<ArrayList<Refactoring>> permutationsList = new ArrayList<ArrayList<Refactoring>>();

			for (IncompleteCompositeDTO incomplete : incompleteCompositeList) {
				RefactoringPermutation permutation = new RefactoringPermutation();

				permutation.performPermutations(incomplete.getRefactorings());

				permutationsList.addAll(permutation.getRefactoringSequencePermutations());
			}

			permutationsList.forEach(permutations -> {

				System.out.println(permutations.toString());
				System.out.println();
			});

			java.util.Map<String, Long> mostCommonIncompleteCompositesRanking = permutationsList.stream()
					.collect(Collectors.groupingBy(permutations -> permutations.toString(), Collectors.counting()));

			// writeMostCommonIncompleteCompositesRanking(mostCommonIncompleteCompositesRanking);

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

	private void writeMostCommonIncompleteCompositesRanking(Map<List<String>, Long> mostCommonIncompleteComposites) {
		CsvWriter csv = new CsvWriter("most-common-incomplete-composites-couchbase-java-client-commit-based.csv", ',',
				Charset.forName("ISO-8859-1"));
		try {
			csv.write("Composite");
			csv.write("Number of occurrence");

			csv.endRecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mostCommonIncompleteComposites.entrySet().forEach(incomplete -> {

			try {
				csv.write(incomplete.getKey().toString());
				csv.write(String.valueOf(incomplete.getValue()));

				csv.endRecord();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		csv.close();
	}

}
