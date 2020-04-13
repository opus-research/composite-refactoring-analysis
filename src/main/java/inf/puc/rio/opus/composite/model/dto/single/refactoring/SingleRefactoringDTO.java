package inf.puc.rio.opus.composite.model.dto.single.refactoring;


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
"refactoringType",
"refactoringId",
"refactoringDetail",
"currentCommit",
"project",
"previousCommit",
"elements"
})
public class SingleRefactoringDTO {

	@JsonProperty("refactoringType")
	private String refactoringType;
	@JsonProperty("refactoringId")
	private String refactoringId;
	@JsonProperty("refactoringDetail")
	private String refactoringDetail;
	@JsonProperty("currentCommit")
	private String currentCommit;
	@JsonProperty("project")
	private String project;
	@JsonProperty("previousCommit")
	private Object previousCommit;
	@JsonProperty("elements")
	private List<CodeElementDTO> elements = null;
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
	public String getCurrentCommit() {
		return currentCommit;
	}

	@JsonProperty("currentCommit")
	public void setCurrentCommit(String currentCommit) {
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

	@JsonProperty("previousCommit")
	public Object getPreviousCommit() {
		return previousCommit;
	}

	@JsonProperty("previousCommit")
	public void setPreviousCommit(Object previousCommit) {
		this.previousCommit = previousCommit;
	}

	@JsonProperty("elements")
	public List<CodeElementDTO> getElements() {
		return elements;
	}

	@JsonProperty("elements")
	public void setElements(List<CodeElementDTO> elements) {
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

}