package opus.inf.puc.rio.composite.analysis;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import inf.puc.rio.opus.composite.model.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.CompositeEffectDTO;
import inf.puc.rio.opus.composite.model.CompositeGroup;
import inf.puc.rio.opus.composite.model.RefactoringTypesEnum;

public class CompositeEffectAnalyzer {
	
	
	
	public static void main(String[] args) {
		
		CompositeEffectAnalyzer analyzer = new CompositeEffectAnalyzer();
		
		List<CompositeEffectDTO> composites = analyzer.getCompositeEffectDTO1("removal-patterns-feature-envy.csv");

		CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();

		//by project
		composites = groupAnalyzer.getRefactoringsNPS(composites);


		//get code smell effect by composite

        analyzer.getCodeSmellEffect(composites);
		// get removed, added, not affected code smells
        analyzer.getEffectComposite(composites);
		//get groups
		Map<String, List<CompositeEffectDTO>> groups = groupAnalyzer.createCompositeGroups(composites);
		List<CompositeGroup> summarizedGroups = groupAnalyzer.summarizeGroups(groups);

		groupAnalyzer.getEffectByGroup(summarizedGroups);




		//get effect by groups 

	}
	
	private void writeCompleteComposite(List<CompositeEffectDTO> composites) {
		
		System.out.println(composites.size());
		CsvWriter csv = new CsvWriter("complete-composites-okhttp.csv", ',',
				Charset.forName("ISO-8859-1"));
		try {
			
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
			
			
			for(CompositeEffectDTO composite : composites) {
				
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

				smellAfter = smellAfter.replace("[", "");
				smellAfter = smellAfter.replace("]", "");
				
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
		
		String[] FILE_HEADER_MAPPING = { "CompositeId","Refactorings","Project","Previous Commit","Current Commit","Smell Type",
										 "Smell_Before","Smell After","Smells Added","Smells Removed","Smells Not Affected"};

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
					
					String previousCommit = record.get("Previous Commit");
					compositeDTO.setPreviousCommit(previousCommit);
					
					String currentCommit = record.get("Current Commit");
					compositeDTO.setCurrentCommit(currentCommit);
					
					
				}
				
			
					
				String smell = record.get("Smell Type");
				String smellBefore = record.get("Smell_Before");
				String smellAfter = record.get("Smell After");
				
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

                if (!codeSmells.containsKey(codeSmellDTOBefore.getType())) {

                    codeSmells.put(codeSmellDTOBefore.getType(), codeSmellDTOBefore);
                    codeSmells.get(codeSmellDTOBefore.getType()).setSmellBefore(1);

                } else {
                    int smellsCount = codeSmells.get(codeSmellDTOBefore.getType()).getBeforeComposite();
                    codeSmells.get(codeSmellDTOBefore.getType()).setSmellBefore(smellsCount + 1);
                }
            }

            for (CodeSmellDTO codeSmellDTOAfter : composite.getCodeSmellsAfter()) {

                if (!codeSmells.containsKey(codeSmellDTOAfter.getType())) {

                    codeSmells.put(codeSmellDTOAfter.getType(), codeSmellDTOAfter);
                    codeSmells.get(codeSmellDTOAfter.getType()).setSmellAfter(1);

                } else {
                    int smellsCount = codeSmells.get(codeSmellDTOAfter.getType()).getAfterComposite();
                    codeSmells.get(codeSmellDTOAfter.getType()).setSmellAfter(smellsCount + 1);
                }
            }


            composite.setCodeSmells(new ArrayList<>(codeSmells.values()));

        }
	}
	
	
	private List<CompositeEffectDTO> getEffectComposite(List<CompositeEffectDTO> composites){
	
		
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
	
	

}
