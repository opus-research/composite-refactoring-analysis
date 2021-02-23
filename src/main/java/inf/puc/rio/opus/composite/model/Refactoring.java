package inf.puc.rio.opus.composite.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Refactoring {

	@JsonProperty("refactoringType")
	private String refactoringType;
	@JsonProperty("refactoringId")
	private String refactoringId;
	@JsonProperty("refactoringDetail")
	private String refactoringDetail;
	@JsonProperty("currentCommit")
	private Commit currentCommit;
	@JsonProperty("project")
	private String project;
	@JsonProperty("elements")
	private List<CodeElement> elements;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("refactoringType")
	public String getRefactoringType() {
		return refactoringType;
	}

	@JsonProperty("refactoringType")
	public void setRefactoringType(String refactoringType) {
		this.refactoringType = refactoringType;
	}

	@JsonProperty("refactoringId")
	public String getRefactoringId() {
		return refactoringId;
	}

	@JsonProperty("refactoringId")
	public void setRefactoringId(String refactoringId) {
		this.refactoringId = refactoringId;
	}

	@JsonProperty("refactoringDetail")
	public String getRefactoringDetail() {
		return refactoringDetail;
	}

	@JsonProperty("refactoringDetail")
	public void setRefactoringDetail(String refactoringDetail) {
		this.refactoringDetail = refactoringDetail;
	}

	@JsonProperty("currentCommit")
	public Commit getCurrentCommit() {
		return currentCommit;
	}

	@JsonProperty("currentCommit")
	public void setCurrentCommit(Commit currentCommit) {
		this.currentCommit = currentCommit;
	}

	@JsonProperty("project")
	public String getProject() {
		return project;
	}

	@JsonProperty("project")
	public void setProject(String project) {
		this.project = project;
	}

	@JsonProperty("elements")
	public List<CodeElement> getElements() {
		return elements;
	}

	@JsonProperty("elements")
	public void setElements(List<CodeElement> elements) {
		this.elements = elements;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return refactoringType ;
	}


	public static boolean equalsToRefactoringType(String refType){

		for(RefactoringTypesEnum refTypeEnum : RefactoringTypesEnum.values()){
			List<String> refTypesList = new ArrayList<String>(Arrays.asList(refTypeEnum.toString().split(",")));

			for(String refTypeAsText : refTypesList) {

				if(refType.trim().equals(refTypeAsText.trim())) {
					return true;
				}
			}


		}
		return false;
	}
	
	

}