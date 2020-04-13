package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		
		List<SingleRefactoringDTO> refs = analyzer.getRefactoringsFromJson();
		
		Map<String, Long> rankingOfRefactoredClasses = analyzer.getRankingOfRefactoredClasses(refs);
		
		rankingOfRefactoredClasses.entrySet().forEach(refactoredClass -> {
			
			System.out.println(refactoredClass.getKey());
			System.out.println(refactoredClass.getValue());

			
			
		});
	
	}
	
	
	public List<SingleRefactoringDTO> getRefactoringsFromJson(){
		
		List<SingleRefactoringDTO> refsList = new ArrayList<SingleRefactoringDTO>();
		ObjectMapper mapper = new ObjectMapper();

		try {

			SingleRefactoringDTO[] refs = mapper.readValue(new File("presto.json"),
					SingleRefactoringDTO[].class);

			refsList = Arrays.asList(refs);

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return refsList;
		
		
	}
	
	
	public Map<String, Long> getRankingOfRefactoredClasses(List<SingleRefactoringDTO> refs) {
		
		
		Map<String, Long> rankingOfRefactoredClasses = new HashMap<String, Long>();
		
		List<String> classesList = new ArrayList<String>();
		
		classesList = getRefactoredClasses(refs);
		
		rankingOfRefactoredClasses  = classesList.stream()
				.collect(Collectors.groupingBy(classPath -> classPath, Collectors.counting()));
		
		
		return rankingOfRefactoredClasses;
		
		
	}
	
	
	
	public List<String> getRefactoredClasses(List<SingleRefactoringDTO> refs){
		
		
		List<String> classes = new ArrayList<String>();
		
		
		for(SingleRefactoringDTO ref : refs) {
		
		if(ref.getElements() != null) {	
		   for(CodeElementDTO elem : ref.getElements()) {
			   
			   if(elem.getClassName() != null) {
				   
				   if(! elem.getClassName().isEmpty()) {
					   
					   classes.add(elem.getClassName());
				   }
			   }
		   }
		}
			
		}
		
		return classes;
		
	}

}
