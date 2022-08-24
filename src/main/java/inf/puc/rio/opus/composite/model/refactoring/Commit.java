package inf.puc.rio.opus.composite.model.refactoring;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"commit",
"previous_commit",
"order_commit"
})
public class Commit {

	@JsonProperty("commit")
	private String commit;
	@JsonProperty("previous_commit")
	private String previous_commit;
	@JsonProperty("order_commit")
	private Integer order_commit;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("commit")
	public String getCommit() {
		return commit;
	}

	@JsonProperty("commit")
	public void setCommit(String commit) {
		this.commit = commit;
	}

	@JsonProperty("previous_commit")
	public String getPrevious_commit() {
		return previous_commit;
	}

	@JsonProperty("previous_commit")
	public void setPrevious_commit(String previous_commit) {
		this.previous_commit = previous_commit;
	}

	@JsonProperty("order_commit")
	public Integer getOrder_commit() {
		return order_commit;
	}

	@JsonProperty("order_commit")
	public void setOrder_commit(Integer order_commit) {
		this.order_commit = order_commit;
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