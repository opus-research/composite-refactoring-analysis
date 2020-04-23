package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.List;

public class CompositeEffectDTO {

	private String Id;
	private String refactorings;
	private String project;
	private String previousCommit;
	private String currentCommit;
	private List<CodeSmellDTO> codeSmells;
	
	
	
	
	public String getId() {
		return Id;
	}
	
	public String getRefactorings() {
		return refactorings;
	}
	
	public List<CodeSmellDTO> getCodeSmells() {
		return codeSmells;
	}
	
	
	public void setId(String compositeId) {
		if(Id == null || Id.isEmpty()) {
			Id = compositeId;
		}		
	}
	
	public void setRefactorings(String refactorings) {
		if(this.refactorings == null || this.refactorings.isEmpty()) {			
			this.refactorings = refactorings;
		}
	}
	
	public void setProject(String project) {
		if(this.project == null || this.project.isEmpty()) {
			this.project = project;
		}
	}
	
	public void setPreviousCommit(String previousCommit) {
		if(this.previousCommit == null || this.previousCommit.isEmpty()) {
			this.previousCommit = previousCommit;
		}
	}
	
	public void setCurrentCommit(String currentCommit) {
		if(this.currentCommit == null || this.currentCommit.isEmpty()) {
			this.currentCommit = currentCommit;
		}
		
	}
	
	public void addSmells(CodeSmellDTO smellDTO) {
		// TODO Auto-generated method stub
		
		if(codeSmells == null) {
			codeSmells = new ArrayList<>();
		}
		
		if(smellDTO != null) {
			codeSmells.add(smellDTO);
		}
	}

	public String getProject() {
		// TODO Auto-generated method stub
		return project;
	}

	public String getPreviousCommit() {
		// TODO Auto-generated method stub
		return previousCommit;
	}

	public String getCurrentCommit() {
		// TODO Auto-generated method stub
		return currentCommit;
	}
	

	
	
	
	
	
	
	
}
