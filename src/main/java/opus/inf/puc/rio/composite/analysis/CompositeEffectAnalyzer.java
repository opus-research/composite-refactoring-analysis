package opus.inf.puc.rio.composite.analysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CompositeEffectAnalyzer {


	public static void main(String[] args) {
		
		CompositeEffectAnalyzer effectAnalyzer = new CompositeEffectAnalyzer();
		CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();
		CompositeAnalyzer compositeAnalyzer = new CompositeAnalyzer();



	}




	private List<CompositeEffectDTO> getCompositeEffectDTOFromJson(String compositeEffectPath){
		ObjectMapper mapper = new ObjectMapper();
		List<CompositeEffectDTO> compositeList = new ArrayList<>();
		try {

			CompositeEffectDTO[] composites = mapper.readValue(new File(compositeEffectPath),
					CompositeEffectDTO[].class);

			compositeList = new ArrayList<CompositeEffectDTO>(Arrays.asList(composites));

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
	
	private void writeCompleteComposite(List<CompositeEffectDTO> completeComposites, String pathCompleteComposites) {

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
			
			
			for(CompositeEffectDTO composite : completeComposites) {
				
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
	
	private List<CompositeEffectDTO> getCompositeEffectDTO1(String path){
		List<CompositeEffectDTO> composites = new ArrayList<CompositeEffectDTO>();
		
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

			CompositeEffectDTO compositeDTO = null;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
			
				String compositeId = record.get("Batch ID");
				
				
				if(!compositeIds.contains(compositeId)) {
					
					compositeIds.add(compositeId);
					
					compositeDTO = new CompositeEffectDTO();
					
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

	
	private List<CompositeEffectDTO> getCompositeEffectDTO(String path){
		
		
		List<CompositeEffectDTO> composites = new ArrayList<CompositeEffectDTO>();
		
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

			CompositeEffectDTO compositeDTO = null;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
			
				String compositeId = record.get("CompositeId");
				
				
				if(!compositeIds.contains(compositeId)) {
					
					compositeIds.add(compositeId);
					
					compositeDTO = new CompositeEffectDTO();
					
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
							
							compositeDTO = new CompositeEffectDTO();
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
	private List<CompositeEffectDTO> getCompleteComposite(List<CompositeEffectDTO> composites){
		
		Set<CompositeEffectDTO> completeComposites = new HashSet<CompositeEffectDTO>();
		
		Set<String> ids = new HashSet<String>(); 
		
		for(CompositeEffectDTO composite : composites) {
			
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
		
		
		return new ArrayList<CompositeEffectDTO>(completeComposites);
	}
	
	
	
	
	
	private void writeGroups(Map<String, List<CompositeEffectDTO>> groups, String path) {
		
		
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
				
				List<CompositeEffectDTO> composites = group.getValue();
				
				for(CompositeEffectDTO composite : composites) {
					
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
	
	private void writeCompositeGroups(Map<String, List<CompositeEffectDTO>> groups, String path) {
		
		
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
				
				List<CompositeEffectDTO> composites = group.getValue();
				
				List<String> ids = new ArrayList<String>();
				
				for(CompositeEffectDTO composite : composites) {
					
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
	
	private void getCodeSmellEffect(List<CompositeEffectDTO> compositeEffectDTOs){

		for(CompositeEffectDTO composite : compositeEffectDTOs) {
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

	private List<CompositeEffectDTO> convertCompositeToCompositeEffectDTO(List<CompositeRefactoring> composites){
		List<CompositeEffectDTO> compositeEffectDTOList = new ArrayList<CompositeEffectDTO>();
		RefactoringAnalyzer refAnalyzer = new RefactoringAnalyzer();

		for (CompositeRefactoring composite : composites){
			CompositeEffectDTO compositeEffectDTO = new CompositeEffectDTO();
			compositeEffectDTO.setId(composite.getId());
			compositeEffectDTO.setProject(composite.getRefactorings().get(0).getProject());
			List<String> refs = refAnalyzer.convertRefactoringListInText(composite.getRefactorings());
			System.out.println(refs.toString());
			compositeEffectDTO.setRefactorings(refs.toString());

			compositeEffectDTOList.add(compositeEffectDTO);
		}
		return compositeEffectDTOList;

	}


	private List<CompositeEffectDTO> getCompositeEffectDetails(List<CompositeEffectDTO> composites){
	
		
		for(CompositeEffectDTO composite : composites) {
			
			
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

	public List<CompositeEffectDTO> getCompositesThatHaveSpecificSmellTypes(List<String> smellTypes, List<CompositeEffectDTO> composites){

		List<CompositeEffectDTO> compositesThatHaveSpecificSmells = new ArrayList<>();

		for(CompositeEffectDTO compositeEffectDTO : composites){
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

	public List<CompositeEffectDTO> getCompositesWithSmellTypesAddition(List<String> smellTypes,  List<CompositeEffectDTO> composites){

		List<CompositeEffectDTO> compositesWithAdditionOfSpecificSmells = new ArrayList<>();

		for(CompositeEffectDTO compositeEffectDTO : composites){
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

	public List<CompositeEffectDTO> getCompositesWithSmellTypesRemoval(List<String> smellTypes,  List<CompositeEffectDTO> composites){

		List<CompositeEffectDTO> compositesWithRemovalOfSmellTypes = new ArrayList<>();

		for(CompositeEffectDTO compositeEffectDTO : composites){
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

	public List<CompositeEffectDTO> getCompositesWithTotalSmellTypesRemoval(List<String> smellTypes,  List<CompositeEffectDTO> composites){

		List<CompositeEffectDTO> compositesWithTotalRemovalOfSmellTypes = new ArrayList<>();

		for(CompositeEffectDTO compositeEffectDTO : composites){
			List<String> existingSmells = new ArrayList<>();

			for (String smellType : smellTypes) {
				for (CodeSmellDTO smell : compositeEffectDTO.getCodeSmells()) {
					if(smell.getType().equals(smellType)) {
						if(smell.getBeforeComposite() == smell.getRemovedSmells()){
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

}
