package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.IncompleteCompositeDTO;
import inf.puc.rio.opus.composite.model.Refactoring;

public class IncompleteCompositeAnalysisMain {
	
	private static long ID = 0;
	
	public static void main(String[] args) throws IOException {
		IncompleteCompositeAnalysisMain analyzer = new IncompleteCompositeAnalysisMain();
		// analyzer.collectIncompleteComposites();
		
		
		analyzer.collectMostCommonIncompleteCompositesIndex();
		

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
	
	
	protected void collectMostCommonIncompleteCompositesIndex() {
		ObjectMapper mapper = new ObjectMapper();
		try {

		
			IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
					"complete-composites-dto/complete-composite-couchbase-java-client.json"),
					IncompleteCompositeDTO[].class);
			
			List<IncompleteCompositeDTO> incompleteCompositeDTOList = Arrays.asList(incompleteComposites);
			
			System.out.println(incompleteCompositeDTOList.get(0).getId());
			
			Map<List<String>, Long> mostCommonIncompleteComposites = new HashMap<List<String>, Long>();
			
			List<List<String>> incompleteCompositeTextList = new ArrayList<List<String>>();
			incompleteCompositeTextList = getIncompleteCompositeTextList(incompleteCompositeDTOList);
			
			Map<List<String>, List<Integer>> incompleteCompositesIDs = new HashMap<List<String>, List<Integer>>();
			
			int[] refListIndex = {0};
			
			for (List<String> refList : incompleteCompositeTextList) {
				
				boolean[] wasFound = {false};
				
				mostCommonIncompleteComposites.entrySet().forEach(refTextList -> {
					
					if (containsAllRefs(refTextList.getKey(), refList)) {
	                    
						long quantityRefList = refTextList.getValue();

						quantityRefList = quantityRefList + 1;

						refTextList.setValue(quantityRefList);
						wasFound[0] = true;
						
						String incompleteCompositeId = incompleteCompositeDTOList.get(refListIndex[0]).getId();
						incompleteCompositesIDs.get(refTextList.getKey()).add(Integer.valueOf(incompleteCompositeId));
					}
				});

				if (!wasFound[0]) {

					mostCommonIncompleteComposites.put(refList, Long.valueOf(1));
					
					List<Integer> refListIndexList = new ArrayList<Integer>();
					String incompleteCompositeId = incompleteCompositeDTOList.get(refListIndex[0]).getId();
				    refListIndexList.add(Integer.valueOf(incompleteCompositeId));
					incompleteCompositesIDs.put(refList, refListIndexList);
					//System.out.println("Add list: " + refList.toString());
				}
			
			    refListIndex[0] = refListIndex[0] + 1;
			}

			mostCommonIncompleteComposites.entrySet().forEach(composite -> {
				System.out.print(composite.getKey());
				System.out.print(composite.getValue());
				System.out.println();
			});
			
			writeMostCommonIncompleteCompositesIndex(mostCommonIncompleteComposites, incompleteCompositesIDs,
					"most-common-complete-composite-couchbase-java-client-range-based.csv");
			
			System.out.println(mostCommonIncompleteComposites.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}



	protected void collectMostCommonIncompleteComposites() {
		ObjectMapper mapper = new ObjectMapper();
		try {

		
			IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
					"incomplete-composites-dto/incomplete-composite-okhttp-range-based-saida.json"),
					IncompleteCompositeDTO[].class);
			
			List<IncompleteCompositeDTO> incompleteCompositeDTOList = Arrays.asList(incompleteComposites);
			
			Map<List<String>, Long> mostCommonIncompleteComposites = new HashMap<List<String>, Long>();
			
			List<List<String>> incompleteCompositeTextList = new ArrayList<List<String>>();
			incompleteCompositeTextList = getIncompleteCompositeTextList(incompleteCompositeDTOList);
			
			for (List<String> refList : incompleteCompositeTextList) {
				
				boolean[] wasFound = {false};
				
				mostCommonIncompleteComposites.entrySet().forEach(refTextList -> {
					
					if (containsAllRefs(refTextList.getKey(), refList)) {
	                    
						long quantityRefList = refTextList.getValue();

						quantityRefList = quantityRefList + 1;

						refTextList.setValue(quantityRefList);
						wasFound[0] = true;
						
					}
				});

				if (!wasFound[0]) {

					mostCommonIncompleteComposites.put(refList, Long.valueOf(1));
					//System.out.println("Add list: " + refList.toString());
				}
			}

			mostCommonIncompleteComposites.entrySet().forEach(composite -> {
				System.out.print(composite.getKey());
				System.out.print(composite.getValue());
				System.out.println();
			});
			
			writeMostCommonIncompleteCompositesRanking(mostCommonIncompleteComposites,
					"most-common-incomplete-composite-okhttp-range-based.csv");
			
			System.out.println(mostCommonIncompleteComposites.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private List<IncompleteCompositeDTO> getAllIncompleteComposites() 
			throws IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		
		
		IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
				"incomplete-composites-dto/incomplete-composite-couchbase-java-client-range-based-saida.json"),
				IncompleteCompositeDTO[].class);
		List<IncompleteCompositeDTO> incompleteCompositeDTOList = Arrays.asList(incompleteComposites);
		System.out.println(incompleteComposites.length);
		
		incompleteComposites =  mapper.readValue(new File(
				"incomplete-composites-dto/incomplete-composite-okhttp-range-based-saida.json"),
				IncompleteCompositeDTO[].class);
		System.out.println(incompleteComposites.length);
		List<IncompleteCompositeDTO> incompleteCompositeDTOList1 = new ArrayList<>(incompleteCompositeDTOList);
		incompleteCompositeDTOList1.addAll(Arrays.asList(incompleteComposites));
		
		
		
		incompleteComposites =  mapper.readValue(new File(
				"incomplete-composites-dto/incomplete-composite-dubbo-range-based-saida.json"),
				IncompleteCompositeDTO[].class);
		System.out.println(incompleteComposites.length);
		List<IncompleteCompositeDTO> incompleteCompositeDTOList2 = new ArrayList<>(incompleteCompositeDTOList1);
		incompleteCompositeDTOList2.addAll(Arrays.asList(incompleteComposites));
			
		
		incompleteComposites =  mapper.readValue(new File(
				"incomplete-composites-dto/incomplete-composite-fresco-range-based-saida.json"),
				IncompleteCompositeDTO[].class);
		System.out.println(incompleteComposites.length);

		List<IncompleteCompositeDTO> incompleteCompositeDTOList3 = new ArrayList<>(incompleteCompositeDTOList2);
		incompleteCompositeDTOList3.addAll(Arrays.asList(incompleteComposites));
		
		
		incompleteComposites =  mapper.readValue(new File(
				"incomplete-composites-dto/incomplete-composite-jgit-range-based-saida.json"),
				IncompleteCompositeDTO[].class);
		System.out.println(incompleteComposites.length);

		List<IncompleteCompositeDTO> incompleteCompositeDTOList4 = new ArrayList<>(incompleteCompositeDTOList3);
		incompleteCompositeDTOList4.addAll(Arrays.asList(incompleteComposites));
	
        return incompleteCompositeDTOList4;	
		
	}



	private List<List<String>> getIncompleteCompositeTextList(List<IncompleteCompositeDTO> incompleteCompositeList) {
		// TODO Auto-generated method stub
		List<List<String>> incompleteCompositeTextList = new ArrayList<List<String>>();
		
		incompleteCompositeList.forEach(incompleteComposite -> {
			
			List<String> incompleteCompositeText = incompleteComposite.getRefactoringsAsTextList();
			incompleteCompositeTextList.add(incompleteCompositeText);
		});
		
		return incompleteCompositeTextList;
	}

	protected void collectMostCommonIncompleteCompositesPerPermutations() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			IncompleteCompositeDTO[] incompleteComposites = mapper.readValue(new File(
					"incomplete-composites-dto/incomplete-composite-couchbase-java-client-range-based-saida.json"),
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

			int count = 0;
			
			if(refactoringTypes.contains("Extract Method")){
				count++;
			}
			if(refactoringTypes.contains("Move Method")){
				count++;
			}
			if(refactoringTypes.contains("Pull Up Method")){
				count++;
			}
			
			if (count > 0) {

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
		
		return refactoringTypesAsText;

	}

	private void writeMostCommonIncompleteCompositesRanking(Map<List<String>, Long> mostCommonIncompleteComposites, String pathfile) {
		CsvWriter csv = new CsvWriter(pathfile, ',',
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
	
	
	private void writeMostCommonIncompleteCompositesIndex(Map<List<String>, Long> mostCommonIncompleteComposites,
			Map<List<String>, List<Integer>> incompleteCompositesIDs, String pathfile) {
		// TODO Auto-generated method stub
			
		CsvWriter csv = new CsvWriter(pathfile, ',', Charset.forName("ISO-8859-1"));
		try {
			csv.write("Composite Ids");
			csv.write("Composite");
			csv.write("Number of occurrence");
			csv.endRecord();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mostCommonIncompleteComposites.entrySet().forEach(incomplete -> {

			try {
				csv.write(incompleteCompositesIDs.get(incomplete.getKey()).toString());
				
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
	
	private boolean containsAllRefs(List<String> refTextList, List<String> refList) {
		List<String> one = new ArrayList<String>();
		List<String> two = new ArrayList<String>();
		one.addAll(refTextList);
		two.addAll(refList);
		Collections.sort(one);
		Collections.sort(two);
		
		return one.equals(two);
	}

}
