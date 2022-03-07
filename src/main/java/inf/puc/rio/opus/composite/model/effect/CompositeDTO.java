package inf.puc.rio.opus.composite.model.effect;

import inf.puc.rio.opus.composite.model.Refactoring;

import java.util.ArrayList;
import java.util.List;

public class CompositeDTO {

	private String Id;
	private String refactoringsAsText;
	private List<Refactoring> refactorings;
	private List<String> refactoringNamesAsList;
	private String project;
	private String previousCommit;
	private String currentCommit;
	private List<CodeSmellDTO> codeSmells;
	private List<CodeSmellDTO> codeSmellsBefore;
	private List<CodeSmellDTO> codeSmellsAfter;
	
	public String getId() {
		return Id;
	}
	
	public String getRefactoringsAsText() {
		return refactoringsAsText;
	}
	
	public List<CodeSmellDTO> getCodeSmells() {
		return codeSmells;
	}
	
	
	public void setId(String compositeId) {
		if(Id == null || Id.isEmpty()) {
			Id = compositeId;
		}		
	}

	public void setRefactoringsAsText(String refactoringsAsText) {
		this.refactoringsAsText = refactoringsAsText;
	}
	
	public void setProject(String project) {
		if(this.project == null) {
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

	public List<CodeSmellDTO> getCodeSmellsBefore() {
		return codeSmellsBefore;
	}

	public void setCodeSmellsBefore(List<CodeSmellDTO> codeSmellsBefore) {
		if(codeSmellsBefore != null){
			this.codeSmellsBefore = codeSmellsBefore;
		}
	}

	public List<CodeSmellDTO> getCodeSmellsAfter() {
		return codeSmellsAfter;
	}

	public void setCodeSmellsAfter(List<CodeSmellDTO> codeSmellsAfter) {
		if(codeSmellsAfter != null){
			this.codeSmellsAfter = codeSmellsAfter;
		}
	}

	public void setCodeSmells(ArrayList<CodeSmellDTO> codeSmells) {
		if(codeSmells != null){
			this.codeSmells = codeSmells;
		}
	}

	public List<Refactoring> getRefactorings() {
		return refactorings;
	}

	public void setRefactorings(List<Refactoring> refactorings) {
		this.refactorings = refactorings;
	}

	public List<String> getRefactoringNamesAsList() {
		return refactoringNamesAsList;
	}

	public void setRefactoringNamesAsList(List<String> refactoringNamesAsList) {
		this.refactoringNamesAsList = refactoringNamesAsList;
	}

	public void setCodeSmells(List<CodeSmellDTO> codeSmells) {
		this.codeSmells = codeSmells;
	}
}
