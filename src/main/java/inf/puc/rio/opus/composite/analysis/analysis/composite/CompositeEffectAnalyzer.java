package inf.puc.rio.opus.composite.analysis.analysis.composite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.analysis.analysis.refactoring.RefactoringAnalyzer;
import inf.puc.rio.opus.composite.analysis.utils.CsvWriter;
import inf.puc.rio.opus.composite.model.*;

import inf.puc.rio.opus.composite.model.effect.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CompositeEffectAnalyzer {


	public static void main(String[] args) {
		
		CompositeEffectAnalyzer effectAnalyzer = new CompositeEffectAnalyzer();
		CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();
		CompositeAnalyzer compositeAnalyzer = new CompositeAnalyzer();



	}




	private List<CompositeDTO> getCompositeEffectDTOFromJson(String compositeEffectPath){
		ObjectMapper mapper = new ObjectMapper();
		List<CompositeDTO> compositeList = new ArrayList<>();
		try {

			CompositeDTO[] composites = mapper.readValue(new File(compositeEffectPath),
					CompositeDTO[].class);

			compositeList = new ArrayList<CompositeDTO>(Arrays.asList(composites));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return compositeList;
	}





	public List<CompositeRefactoring> filterCompositeByIds(List<String> ids, List<CompositeRefactoring> allComposites){

		List<CompositeRefactoring> filteredComposites = new ArrayList<>();
		for(String id: ids){

			for(CompositeRefactoring composite : allComposites){

				if(composite.getId().equals(id)){
					filteredComposites.add(composite);
				}
			}
		}

		return filteredComposites;
	}
	
	private void writeCompleteComposite(List<CompositeDTO> completeComposites, String pathCompleteComposites) {

		ObjectMapper mapper = new ObjectMapper();

		System.out.println(completeComposites.size());
		CsvWriter csv = new CsvWriter(pathCompleteComposites + ".csv", ',',
				Charset.forName("ISO-8859-1"));
		try {
			mapper.writeValue(new File( pathCompleteComposites +".json"), completeComposites);
			csv.write("CompositeId");
			csv.write("Refactorings");
			csv.write("Project");
			csv.write("Previous Commit");
			csv.write("Current Commit");
			csv.write("Smell Type");
			csv.write("Smell Before");
			csv.write("Smell After");
			csv.write("Smells Added");
			csv.write("Smells Removed");
			csv.write("Smells Not Affected");
			csv.endRecord();
			
			
			for(CompositeDTO composite : completeComposites) {
				
				for(CodeSmellDTO smell : composite.getCodeSmells()) {
					
					csv.write(composite.getId());
					
					System.out.println(composite.getId());
					
					csv.write(composite.getRefactorings());
					csv.write(composite.getProject());
					csv.write(composite.getPreviousCommit());
					csv.write(composite.getCurrentCommit());
					
					csv.write(smell.getType());
					csv.write(String.valueOf(smell.getBeforeComposite()));
					csv.write(String.valueOf(smell.getAfterComposite()));
					csv.write(String.valueOf(smell.getAddedSmells()));
					csv.write(String.valueOf(smell.getRemovedSmells()));
					csv.write(String.valueOf(smell.getNotAffectSmells()));
					
					csv.endRecord();
					
				}
				
			
			}
			
			csv.close();
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private List<CompositeDTO> getCompositeEffectDTO1(String path){
		List<CompositeDTO> composites = new ArrayList<CompositeDTO>();
		
		String[] FILE_HEADER_MAPPING = { "Validator", "projectName", "Batch ID", "Refactorings", "Code Elements",
				"Start Commit", "Smells Before", "Smell Before IDs", 
				"Smells Before Starting Line", "Smell Before Ending Line", "Smell Before Reason",	"End Commit", "Smells After",	
				"Smell After IDs",	"Smells After Starting Line", "Smell After Ending Line", "Smell After Reason"};

		List csvRecords;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(
								  FILE_HEADER_MAPPING);
		FileReader fileReader;
	
		try {
			fileReader = new FileReader(path);

			CSVParser csvFileParser;
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			csvRecords = csvFileParser.getRecords();

			Set<String> compositeIds = new HashSet<String>();

			CompositeDTO compositeDTO = null;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
			
				String compositeId = record.get("Batch ID");
				
				
				if(!compositeIds.contains(compositeId)) {
					
					compositeIds.add(compositeId);
					
					compositeDTO = new CompositeDTO();
					
					compositeDTO.setId(compositeId);
					
					String refactorings = record.get("Refactorings");
					refactorings = refactorings.replace("\"", "");
					compositeDTO.setRefactorings(refactorings);
					
					String project = record.get("projectName");
					compositeDTO.setProject(project);

					//Ta pegando uma lista de commits
					String previousCommits = record.get("Start Commit");
					List<String> commits = new ArrayList<String>(Arrays.asList(previousCommits.split(",")));
					String previousCommit = commits.get(0).replace("[", "");
					compositeDTO.setPreviousCommit(previousCommit);
					
					String currentCommits = record.get("End Commit");
					commits = new ArrayList<String>(Arrays.asList(currentCommits.split(",")));
					String currentCommit = commits.get(commits.size()-1).replace("]", "");
					compositeDTO.setCurrentCommit(currentCommit);
						
				}
					
				String smellBefore = record.get("Smells Before");
				String smellAfter = record.get("Smells After");
				
				smellBefore = smellBefore.replace("[", "");
				smellBefore = smellBefore.replace("]", "");
                smellBefore = smellBefore.replace("\"", "");

				smellAfter = smellAfter.replace("[", "");
				smellAfter = smellAfter.replace("]", "");
                smellAfter = smellAfter.replace("\"", "");
				
				List<String> smellBeforeList = new ArrayList<String>(Arrays.asList(smellBefore.split(",")));;
				List<String> smellAfterList = new ArrayList<String>(Arrays.asList(smellAfter.split(",")));; 
				
				List<CodeSmellDTO> smellsDTOBeforeList = new ArrayList<>();
				for(String smellType : smellBeforeList) {
					CodeSmellDTO smellDTO = new CodeSmellDTO();
					
				    smellDTO.setType(smellType);
				    smellsDTOBeforeList.add(smellDTO);    
				}
				
				List<CodeSmellDTO> smellsDTOAfterList = new ArrayList<>();
				for(String smellType : smellAfterList) {
					CodeSmellDTO smellDTO = new CodeSmellDTO();
					
				    smellDTO.setType(smellType);
				    smellsDTOAfterList.add(smellDTO);    
				}

				compositeDTO.setCodeSmellsBefore(smellsDTOBeforeList);
				compositeDTO.setCodeSmellsAfter(smellsDTOAfterList);

			    //Adicionar o composite quando todos os smells forem adicionados na lista 
			    if(compositeDTO != null) {
			    	composites.add(compositeDTO);
			    }
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return composites;
	}

	
	public List<CompositeDTO> getCompositeEffectDTO(String path){
		
		
		List<CompositeDTO> composites = new ArrayList<CompositeDTO>();
		
		String[] FILE_HEADER_MAPPING = { "CompositeId","Refactorings","Project","PreviousCommit","CurrentCommit","SmellType",
										 "SmellBefore", "SmellAfter"};

		List csvRecords;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(
								  FILE_HEADER_MAPPING);
		FileReader fileReader;
	
		try {
			fileReader = new FileReader(path);

			CSVParser csvFileParser;
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			csvRecords = csvFileParser.getRecords();

			Set<String> compositeIds = new HashSet<String>();

			CompositeDTO compositeDTO = null;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
			
				String compositeId = record.get("CompositeId");
				
				
				if(!compositeIds.contains(compositeId)) {
					
					compositeIds.add(compositeId);
					
					compositeDTO = new CompositeDTO();
					
					compositeDTO.setId(compositeId);
					
					String refactorings = record.get("Refactorings");
					compositeDTO.setRefactorings(refactorings);
					
					String project = record.get("Project");
					compositeDTO.setProject(project);
					
					String previousCommit = record.get("PreviousCommit");
					compositeDTO.setPreviousCommit(previousCommit);
					
					String currentCommit = record.get("CurrentCommit");
					compositeDTO.setCurrentCommit(currentCommit);
					
					
				}
				
			
					
				String smell = record.get("SmellType");
				String smellBefore = record.get("SmellBefore");
				String smellAfter = record.get("SmellAfter");
				
				CodeSmellDTO smellDTO = new CodeSmellDTO();
				
			    smellDTO.setType(smell);
			    
			    
			    smellDTO.setSmellBefore(Integer.valueOf(smellBefore));
			    smellDTO.setSmellAfter(Integer.valueOf(smellAfter));
			    
			    compositeDTO.addSmells(smellDTO);
			    
			    
			    //Adicionar o composite quando todos os smells forem adicionados na lista 
			    if(compositeDTO != null) {
					
			    	//Adicionar o corrente composite quando o próximo composite for diferente  
			    	if(i+1 < csvRecords.size()) {
			    		
			    		CSVRecord recordNext = (CSVRecord) csvRecords.get(i+1);
						
						String compositeNextId = recordNext.get("CompositeId");
						
						if(!compositeIds.contains(compositeNextId)) {
							composites.add(compositeDTO);
							
							compositeDTO = new CompositeDTO();
						}	
			    	}
			    	//Adicionar o corrente composite quando é o último composite
			    	if(i+1 == csvRecords.size()) {
			    		composites.add(compositeDTO);
			    	}
			    	
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return composites;
		
	}
	
	//TODO - Implementar esse metodo em termos de valores de removed, added e not_affected
	public List<CompositeDTO> getCompleteComposite(List<CompositeDTO> composites){
		
		Set<CompositeDTO> completeComposites = new HashSet<CompositeDTO>();
		
		Set<String> ids = new HashSet<String>(); 
		
		for(CompositeDTO composite : composites) {
			
			for(CodeSmellDTO smell : composite.getCodeSmells()) {
				
				if(smell.getType().equals("LongMethod") || smell.getType().equals("FeatureEnvy")
						|| smell.getType().equals("GodClass") || smell.getType().equals("ComplexClass")) {
					
					if(smell.getAfterComposite() < smell.getBeforeComposite()) {
						
						completeComposites.add(composite);
						
						ids.add(composite.getId());
						
					}
				}
				
			}
		}
		
		
		for(String id: ids) {
			
			System.out.println(id);
			
		}
		
		
		return new ArrayList<CompositeDTO>(completeComposites);
	}
	
	
	
	
	
	private void writeGroups(Map<String, List<CompositeDTO>> groups, String path) {
		
		
		CsvWriter csv = new CsvWriter(path, ',',
				Charset.forName("ISO-8859-1"));
		try {
			
			csv.write("Group");
			csv.write("Group Size");
			csv.write("CompositeId");
			csv.write("Refactorings");
			csv.write("Project");
			csv.write("Previous Commit");
			csv.write("Current Commit");
			csv.write("Smell Type");
			csv.write("Smell Before");
			csv.write("Smell After");
			csv.write("Smells Added");
			csv.write("Smells Removed");
			csv.write("Smells Not Affected");
			csv.endRecord();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		groups.entrySet().forEach(group -> {
			
			
			try {
				
				List<CompositeDTO> composites = group.getValue();
				
				for(CompositeDTO composite : composites) {
					
					for(CodeSmellDTO smell : composite.getCodeSmells()) {
						
						csv.write(group.getKey());
						csv.write(String.valueOf(composites.size()));
					    csv.write(composite.getId());
						String refactorings = composite.getRefactorings();
						csv.write(refactorings);
						csv.write(composite.getProject());
						csv.write(composite.getPreviousCommit());
						csv.write(composite.getCurrentCommit());
						
						csv.write(smell.getType());
						csv.write(String.valueOf(smell.getBeforeComposite()));
						csv.write(String.valueOf(smell.getAfterComposite()));
						csv.write(String.valueOf(smell.getAddedSmells()));
						csv.write(String.valueOf(smell.getRemovedSmells()));
						csv.write(String.valueOf(smell.getNotAffectSmells()));
						
						 csv.endRecord();
						
					}
					
				
				}
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		csv.close();
		
	}
	
	private void writeCompositeGroups(Map<String, List<CompositeDTO>> groups, String path) {
		
		
		CsvWriter csv = new CsvWriter(path, ',',
				Charset.forName("ISO-8859-1"));
		try {
			
			csv.write("Group");
			csv.write("Group Size");
			csv.write("CompositeId");
			
			csv.endRecord();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		groups.entrySet().forEach(group -> {
			
			try {
				
				List<CompositeDTO> composites = group.getValue();
				
				List<String> ids = new ArrayList<String>();
				
				for(CompositeDTO composite : composites) {
					
					ids.add(composite.getId());
				}
				
				csv.write(group.getKey());
				csv.write(String.valueOf(composites.size()));
			    csv.write(ids.toString());
			    csv.endRecord();
					
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		csv.close();
		
	}
	
	private void getCodeSmellEffect(List<CompositeDTO> compositeEffectDTOs){

		for(CompositeDTO composite : compositeEffectDTOs) {
            HashMap<String, CodeSmellDTO> codeSmells = new HashMap<>();


            for (CodeSmellDTO codeSmellDTOBefore : composite.getCodeSmellsBefore()) {

                if (!codeSmells.containsKey(codeSmellDTOBefore.getType().trim())) {

                    codeSmells.put(codeSmellDTOBefore.getType().trim(), codeSmellDTOBefore);
                    codeSmells.get(codeSmellDTOBefore.getType().trim()).setSmellBefore(1);

                } else {
                    int smellsCount = codeSmells.get(codeSmellDTOBefore.getType().trim()).getBeforeComposite();
                    codeSmells.get(codeSmellDTOBefore.getType().trim()).setSmellBefore(smellsCount + 1);
                }
            }

            for (CodeSmellDTO codeSmellDTOAfter : composite.getCodeSmellsAfter()) {

                if (!codeSmells.containsKey(codeSmellDTOAfter.getType().trim())) {

                    codeSmells.put(codeSmellDTOAfter.getType().trim(), codeSmellDTOAfter);
                    codeSmells.get(codeSmellDTOAfter.getType().trim()).setSmellAfter(1);

                } else {
                    int smellsCount = codeSmells.get(codeSmellDTOAfter.getType().trim()).getAfterComposite();
                    codeSmells.get(codeSmellDTOAfter.getType().trim()).setSmellAfter(smellsCount + 1);
                }
            }


            composite.setCodeSmells(new ArrayList<>(codeSmells.values()));

        }
	}

	private List<CompositeDTO> convertCompositeToCompositeEffectDTO(List<CompositeRefactoring> composites){
		List<CompositeDTO> compositeEffectDTOList = new ArrayList<CompositeDTO>();
		RefactoringAnalyzer refAnalyzer = new RefactoringAnalyzer();

		for (CompositeRefactoring composite : composites){
			CompositeDTO compositeEffectDTO = new CompositeDTO();
			compositeEffectDTO.setId(composite.getId());
			//compositeEffectDTO.setProject(composite.getRefactorings().get(0).getProject());
			//List<String> refs = refAnalyzer.convertRefactoringListInText(composite.getRefactorings());
			//System.out.println(refs.toString());
			//compositeEffectDTO.setRefactorings(refs.toString());

			compositeEffectDTOList.add(compositeEffectDTO);
		}
		return compositeEffectDTOList;

	}


	private List<CompositeDTO> getCompositeEffectDetails(List<CompositeDTO> composites){
	
		
		for(CompositeDTO composite : composites) {
			
			
			for(CodeSmellDTO smell : composite.getCodeSmells()) {
				
				
				if(smell.getBeforeComposite() > smell.getAfterComposite()) {	
					int smellRemoved = smell.getBeforeComposite() - smell.getAfterComposite();
					smell.setRemovedSmells(smellRemoved);
				}
				
				if(smell.getAfterComposite() > smell.getBeforeComposite()) {
					int smellAdded = smell.getAfterComposite() - smell.getBeforeComposite();
					smell.setAddedSmells(smellAdded);
				}
				
				if(smell.getAfterComposite() >= smell.getBeforeComposite()) {
					int smellNotAffected = smell.getBeforeComposite();
					smell.setNotAffectSmells(smellNotAffected);
				}
				
				if(smell.getAfterComposite() < smell.getBeforeComposite()) {
					int smellNotAffected = smell.getAfterComposite();
					smell.setNotAffectSmells(smellNotAffected);
				}
				
				
			}
			
		}
		return composites; 
	}

	public List<CompositeDTO> getCompositesThatHaveSpecificSmellTypes(List<String> smellTypes, List<CompositeDTO> composites){

		List<CompositeDTO> compositesThatHaveSpecificSmells = new ArrayList<>();

		for(CompositeDTO compositeEffectDTO : composites){
			List<String> existingSmells = new ArrayList<>();

			for (String smellType : smellTypes) {
				for (CodeSmellDTO smell : compositeEffectDTO.getCodeSmells()) {
					if(smell.getType().equals(smellType)) {
						existingSmells.add(smell.getType());
					}
				}
			}

			Collections.sort(smellTypes);
			Collections.sort(existingSmells);

			if(existingSmells.toString().equals(smellTypes.toString())){
				compositesThatHaveSpecificSmells.add(compositeEffectDTO);
			}
		}

		return compositesThatHaveSpecificSmells;

	}

	public List<CompositeDTO> getCompositesWithSmellTypesAddition(List<String> smellTypes, List<CompositeDTO> composites){

		List<CompositeDTO> compositesWithAdditionOfSpecificSmells = new ArrayList<>();

		for(CompositeDTO compositeEffectDTO : composites){
			List<String> existingSmells = new ArrayList<>();

			for (String smellType : smellTypes) {
				for (CodeSmellDTO smell : compositeEffectDTO.getCodeSmells()) {
					if(smell.getType().equals(smellType)) {
						if(smell.getAddedSmells() > 0){
							existingSmells.add(smell.getType());
						}

					}
				}
			}

			Collections.sort(smellTypes);
			Collections.sort(existingSmells);

			if(existingSmells.toString().equals(smellTypes.toString())){
				compositesWithAdditionOfSpecificSmells.add(compositeEffectDTO);
			}
		}

		return compositesWithAdditionOfSpecificSmells;

	}

	public List<CompositeDTO> getCompositesWithSmellTypesRemoval(List<String> smellTypes, List<CompositeDTO> composites){

		List<CompositeDTO> compositesWithRemovalOfSmellTypes = new ArrayList<>();

		for(CompositeDTO compositeEffectDTO : composites){
			List<String> existingSmells = new ArrayList<>();

			for (String smellType : smellTypes) {
				for (CodeSmellDTO smell : compositeEffectDTO.getCodeSmells()) {
					if(smell.getType().equals(smellType)) {
						if(smell.getRemovedSmells() > 0){
							existingSmells.add(smell.getType());
						}
					}
				}
			}

			Collections.sort(smellTypes);
			Collections.sort(existingSmells);

			if(existingSmells.toString().equals(smellTypes.toString())){
				compositesWithRemovalOfSmellTypes.add(compositeEffectDTO);
			}
		}

		return compositesWithRemovalOfSmellTypes;
	}

	public List<CompositeDTO> getCompositesWithTotalSmellTypesRemoval(List<String> smellTypes, List<CompositeDTO> composites){

		List<CompositeDTO> compositesWithTotalRemovalOfSmellTypes = new ArrayList<>();

		for(CompositeDTO compositeEffectDTO : composites){
			List<String> existingSmells = new ArrayList<>();

			for (String smellType : smellTypes) {
				for (CodeSmellDTO smell : compositeEffectDTO.getCodeSmells()) {
					if(smell.getType().equals(smellType)) {
						if(smell.getAfterComposite() == 0){
							existingSmells.add(smell.getType());
						}
					}
				}
			}

			Collections.sort(smellTypes);
			Collections.sort(existingSmells);

			if(existingSmells.toString().equals(smellTypes.toString())){
				compositesWithTotalRemovalOfSmellTypes.add(compositeEffectDTO);
			}
		}

		return compositesWithTotalRemovalOfSmellTypes;
	}

	public void writeCompositeEffectAsJson(List<CompositeDTO> compositeDTOList, String fileName) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(fileName),  compositeDTOList);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
