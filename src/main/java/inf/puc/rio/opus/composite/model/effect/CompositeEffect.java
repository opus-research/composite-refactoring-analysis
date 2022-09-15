package inf.puc.rio.opus.composite.model.effect;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import inf.puc.rio.opus.composite.model.refactoring.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.smell.CodeSmell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeEffect {

    @JsonProperty("id")
    private String id;

    @JsonProperty("compositeId")
    private String compositeId;

    @JsonProperty("composite")
    private CompositeRefactoring composite;

    @JsonProperty("smellsBefore")
    private List<String> smellsBefore;

    @JsonProperty("smellsAfter")
    private List<String> smellsAfter;

    @JsonProperty("codeSmellsBefore")
    private List<CodeSmell> codeSmellsBefore;

    @JsonProperty("codeSmellsAfter")
    private List<CodeSmell> codeSmellsAfter;

    @JsonProperty("currentCommit")
    private String currentCommit;

    @JsonProperty("previousCommit")
    private String previousCommit;


    public CompositeEffect(String id,
                           String refactoringId,
                           List<String> smellsBefore,
                           List<String> smellsAfter) {
        this.id = id;
        this.compositeId = refactoringId;
        this.smellsBefore = smellsBefore;
        this.smellsAfter = smellsAfter;
    }


    public CompositeEffect(String id,
                           CompositeRefactoring composite,
                           List<CodeSmell> codeSmellsBefore,
                           List<CodeSmell> codeSmellsAfter) {
        this.id = id;
        this.composite = composite;
        this.codeSmellsBefore = codeSmellsBefore;
        this.codeSmellsAfter = codeSmellsAfter;
    }

    public CompositeEffect(){

    }

    public String getCurrentCommit() {
        return currentCommit;
    }

    public void setCurrentCommit(String currentCommit) {
        this.currentCommit = currentCommit;
    }

    public String getPreviousCommit() {
        return previousCommit;
    }

    public void setPreviousCommit(String previousCommit) {
        this.previousCommit = previousCommit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(String compositeId) {
        this.compositeId = compositeId;
    }

    public List<String> getSmellsBefore() {
        return smellsBefore;
    }

    public void setSmellsBefore(List<String> smellsBefore) {
        this.smellsBefore = smellsBefore;
    }

    public List<String> getSmellsAfter() {
        return smellsAfter;
    }

    public void setSmellsAfter(List<String> smellsAfter) {
        this.smellsAfter = smellsAfter;
    }

    public CompositeRefactoring getComposite() {
        return composite;
    }

    public void setComposite(CompositeRefactoring composite) {
        this.composite = composite;
    }

    public List<CodeSmell> getCodeSmellsBefore() {
        return codeSmellsBefore;
    }

    public void setCodeSmellsBefore(List<CodeSmell> codeSmellsBefore) {
        this.codeSmellsBefore = codeSmellsBefore;
    }

    public List<CodeSmell> getCodeSmellsAfter() {
        return codeSmellsAfter;
    }

    public void setCodeSmellsAfter(List<CodeSmell> codeSmellsAfter) {
        this.codeSmellsAfter = codeSmellsAfter;
    }


}
