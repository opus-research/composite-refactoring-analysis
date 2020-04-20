package opus.inf.puc.rio.composite.analysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import inf.puc.rio.opus.composite.model.CodeSmellDTO;
import inf.puc.rio.opus.composite.model.CompositeEffectDTO;

public class CompositeEffectAnalyzer {
	
	
	
	
	private List<CompositeEffectDTO> getCompositeEffectDTO(){
		
		
		
		List<CompositeEffectDTO> composites = new ArrayList<CompositeEffectDTO>();
		
		String[] FILE_HEADER_MAPPING = { "CompositeId","Refactorings","Project","Previous Commit","Current Commit","Smell Type",
										 "Smell_Before","Smell After","Smells Added","Smells Removed","Smells Not Affected"};

		List csvRecords;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(
								  FILE_HEADER_MAPPING);
		FileReader fileReader;
	
		try {
			fileReader = new FileReader("positive-composites.csv");

			CSVParser csvFileParser;
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			csvRecords = csvFileParser.getRecords();

			Set<String> compositeIds = new HashSet<String>();

			CompositeEffectDTO compositeDTO = null;
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
			
				String compositeId = record.get("CompositeId");
				
				if(!compositeIds.contains(compositeId)) {
					
					if(compositeDTO != null) {
						
						composites.add(compositeDTO);
					}
					
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
					
					
					String smell = record.get("Smell Type");
					String smellBefore = record.get("Smell_Before");
					String smellAfter = record.get("Smell After");
					
					CodeSmellDTO smellDTO = new CodeSmellDTO();
					
				    smellDTO.setType(smell);
				    
				    
				    smellDTO.setSmellBefore(Integer.valueOf(smellBefore));
				    smellDTO.setSmellAfter(Integer.valueOf(smellAfter));
				    
				    compositeDTO.addSmells(smellDTO);
					
					
				}
				
				else {
					
					String smell = record.get("Smell Type");
					String smellBefore = record.get("Smell_Before");
					String smellAfter = record.get("Smell After");
					
					CodeSmellDTO smellDTO = new CodeSmellDTO();
					
				    smellDTO.setType(smell);
				    
				    
				    smellDTO.setSmellBefore(Integer.valueOf(smellBefore));
				    smellDTO.setSmellAfter(Integer.valueOf(smellAfter));
				    
				    compositeDTO.addSmells(smellDTO);
					
					
					
				}
				
				
				

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return composites;
		
	}
	
	
	private List<CompositeEffectDTO> getCompleteComposite(List<CompositeEffectDTO> composites){
		
		List<CompositeEffectDTO> completeComposites = new ArrayList<CompositeEffectDTO>();
		
		
		for(CompositeEffectDTO composite : composites) {
			
			for(CodeSmellDTO smell : composite.getCodeSmells()) {
				
				if(smell.getType().equals("LongMethod") || 
				   smell.getType().equals("GodClass")   || 
				   smell.getType().equals("FeatureEnvy") ||
				   smell.getType().equals("ComplexClass")) {
					
					if(smell.getAfterComposite() < smell.getBeforeComposite()) {
						
						
						completeComposites.add(composite);
						
					}
				}
				
			}
		}
		
		
		return completeComposites;
	}

}
