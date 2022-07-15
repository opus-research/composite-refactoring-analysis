package inf.puc.rio.opus.composite.model.effect;

import inf.puc.rio.opus.composite.model.Refactoring;

import java.util.ArrayList;
import java.util.List;

public class CompositeDTO {

	private String Id;
	private String refactorings;
	private List<Refactoring> refactoringsList;
	private List<String> refactoringsAsTextList;
	private String project;
	private String previousCommit;
	private String currentCommit;
	private List<CodeSmellDTO> codeSmells;
	private List<CodeSmellDTO> codeSmellsBefore;
	private List<CodeSmellDTO> codeSmellsAfter;
	
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
		this.refactorings = refactorings;
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

	public List<Refactoring> getRefactoringsList() {
		return refactoringsList;
	}

	public void setRefactoringsList(List<Refactoring> refactoringsList) {
		this.refactoringsList = refactoringsList;
	}

	public List<String> getRefactoringsAsTextList() {
		return refactoringsAsTextList;
	}

	public void setRefactoringsAsTextList(List<String> refactoringsAsTextList) {
		this.refactoringsAsTextList = refactoringsAsTextList;
	}



}
