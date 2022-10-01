package inf.puc.rio.opus.composite.model.effect;

import inf.puc.rio.opus.composite.model.refactoring.Refactoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeDTOldModel {
    private String id;
    private List<Refactoring> refactorings = new ArrayList<Refactoring>();
    private String type;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Refactoring> getRefactorings() {
        return refactorings;
    }
    public void setRefactorings(List<Refactoring> refactorings) {
        this.refactorings = refactorings;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}