package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CodeElement;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.CodeElementDTO;
import inf.puc.rio.opus.composite.model.dto.single.refactoring.SingleRefactoringDTO;

public class RefactoringAnalyzer {

	public static void main(String[] args) {

		RefactoringAnalyzer analyzer = new RefactoringAnalyzer();

		List<SingleRefactoringDTO> refs = analyzer.getRefactoringsFromJson("elasticsearch.json");

		Map<String, Long> rankingOfRefactoredClasses = analyzer.getRankingOfRefactoredClasses(refs);

		rankingOfRefactoredClasses.entrySet().forEach(refactoredClass -> {

			System.out.println(refactoredClass.getKey());
			System.out.println(refactoredClass.getValue());
		});

		// analyzer.writeRefactoredClassesRanking(rankingOfRefactoredClasses);

		Map<String, Set<SingleRefactoringDTO>> refsByRefactoredClasses = analyzer.getRefsByRefactoredClasses(rankingOfRefactoredClasses, refs);
		
		analyzer.writeRefactoredClassesRanking(rankingOfRefactoredClasses, refsByRefactoredClasses);
	}

	private void writeRefactoredClassesRanking(Map<String, Long> rankingOfRefactoredClasses,
			 								  Map<String, Set<SingleRefactoringDTO>> refsByRefactoredClasses) {
		// TODO Auto-generated method stub
		CsvWriter csv = new CsvWriter("rankingOfRefactoredClasses-elasticsearch-main-types.csv", ',', Charset.forName("ISO-8859-1"));
		try {
			csv.write("Class");
			csv.write("Number of Occurrence");
			csv.write("Number of Refactorings");
			csv.write("Refactoring ID");
			csv.write("Refactoring Type");
			csv.write("Commit");
			csv.endRecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rankingOfRefactoredClasses.entrySet().forEach(refClass -> {

			try {
				
				
			
				for (SingleRefactoringDTO ref : refsByRefactoredClasses.get(refClass.getKey())) {
					
					System.out.println(ref.getRefactoringType());
					
					
								//Refactored Class
								csv.write(refClass.getKey());
								
								//Number of Occurrence
								csv.write(String.valueOf(refClass.getValue()));
								
								//Number of Refactorings 
								int numberOfRefs = refsByRefactoredClasses.get(refClass.getKey()).size();
								csv.write(String.valueOf(numberOfRefs));
								
								//Refactoring ID 
								csv.write(ref.getRefactoringId());
								
								//Refactoring Type
								csv.write(ref.getRefactoringType());
								
								//Commit
								csv.write(ref.getCurrentCommit());
								csv.endRecord();
					
					
				
				}
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		csv.close();
		
	}

	private Map<String, Set<SingleRefactoringDTO>> getRefsByRefactoredClasses(Map<String, Long> rankingOfRefactoredClasses, List<SingleRefactoringDTO> refs) {
		// TODO Auto-generated method stub, 
		
		
		Map<String, Set<SingleRefactoringDTO>> refsByRefactoredClasses = new HashMap<String, Set<SingleRefactoringDTO>>();
		
		for (Entry<String, Long> refactoreClass : rankingOfRefactoredClasses.entrySet()) {

			Set<SingleRefactoringDTO> refsList = new HashSet<SingleRefactoringDTO>();
			
			for (SingleRefactoringDTO ref : refs) {

				if (ref.getElements() != null) {
					for (CodeElementDTO elem : ref.getElements()) {
						
						if (elem.getClassName() != null) {

							if (elem.getClassName().equals(refactoreClass.getKey())) {

								refsList.add(ref);
							}
						}
					}
				}
			}
			
			refsByRefactoredClasses.put(refactoreClass.getKey(), refsList);
			
		}
		
		return refsByRefactoredClasses;
		
	
	}

	private List<SingleRefactoringDTO> getRefactoringsFromJson(String jsonPath) {

		List<SingleRefactoringDTO> refsList = new ArrayList<SingleRefactoringDTO>();
		ObjectMapper mapper = new ObjectMapper();

		try {

			SingleRefactoringDTO[] refs = mapper.readValue(new File(jsonPath),
					SingleRefactoringDTO[].class);

			refsList = Arrays.asList(refs);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return refsList;

	}

	private Map<String, Long> getRankingOfRefactoredClasses(List<SingleRefactoringDTO> refs) {

		Map<String, Long> rankingOfRefactoredClasses = new HashMap<String, Long>();

		List<String> classesList = new ArrayList<String>();

		classesList = getRefactoredClasses(refs);

		rankingOfRefactoredClasses = classesList.stream()
				.collect(Collectors.groupingBy(classPath -> classPath, Collectors.counting()));

		return rankingOfRefactoredClasses;

	}

	private List<String> getRefactoredClasses(List<SingleRefactoringDTO> refs) {

		List<String> classes = new ArrayList<String>();

		for (SingleRefactoringDTO ref : refs) {

			if (ref.getElements() != null) {
				for (CodeElementDTO elem : ref.getElements()) {

					if (elem.getClassName() != null) {

						if (!elem.getClassName().isEmpty()) {

							classes.add(elem.getClassName());
						}
					}
				}
			}

		}

		return classes;

	}

	private void writeRefactoredClassesRanking(Map<String, Long> rankingOfRefactoredClasses) {
		CsvWriter csv = new CsvWriter("rankingOfRefactoredClasses.csv", ',', Charset.forName("ISO-8859-1"));
		try {
			csv.write("Class");
			csv.write("Number of occurrence");
			csv.endRecord();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rankingOfRefactoredClasses.entrySet().forEach(refClass -> {

			try {
				csv.write(refClass.getKey().toString());
				csv.write(String.valueOf(refClass.getValue()));

				csv.endRecord();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		csv.close();
	}

}
