package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"refactorings",
"type"
})
public class IncompleteCompositeDTO {
	
	@JsonProperty("refactorings")
	private List<Refactoring> refactorings = null;
	
	@JsonProperty("type")
	private String type;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	@JsonProperty("refactorings")
	public List<Refactoring> getRefactorings() {
		return refactorings;
	}

	@JsonProperty("refactorings")
	public void setRefactorings(List<Refactoring> refactorings) {
		this.refactorings = refactorings;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public String toString() {
		List<String> refactoringTypesList = new ArrayList<String>();

		for (Refactoring refactoring : refactorings) {

			refactoringTypesList.add(refactoring.getRefactoringType());

		}
		String refactoringTypesAsText = String.join(",", refactoringTypesList);
		
		return refactoringTypesAsText;
	}
	
	public List<String> getRefactoringsAsTextList(){
		
		List<String> refactoringTypesList = new ArrayList<String>();

		for (Refactoring refactoring : refactorings) {

			refactoringTypesList.add(refactoring.getRefactoringType());

		}
		return refactoringTypesList;
	}
	
}
