package inf.puc.rio.opus.composite.model.dto.single.refactoring.refMiner.output;

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
        "repository",
        "sha1",
        "url",
        "refactorings"
})
public class CommitOutput {

    @JsonProperty("repository")
    private String repository;
    @JsonProperty("sha1")
    private String sha1;
    @JsonProperty("url")
    private String url;
    @JsonProperty("refactorings")
    private List<RefactoringOutput> refactorings = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("repository")
    public String getRepository() {
        return repository;
    }

    @JsonProperty("repository")
    public void setRepository(String repository) {
        this.repository = repository;
    }

    @JsonProperty("sha1")
    public String getSha1() {
        return sha1;
    }

    @JsonProperty("sha1")
    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("refactorings")
    public List<RefactoringOutput> getRefactorings() {
        return refactorings;
    }

    @JsonProperty("refactorings")
    public void setRefactorings(List<RefactoringOutput> refactorings) {
        this.refactorings = refactorings;
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


