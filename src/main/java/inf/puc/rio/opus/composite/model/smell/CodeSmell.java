package inf.puc.rio.opus.composite.model.smell;



public class CodeSmell {

    private String smellId;
    private String name;
    private String projectName;
    private String codeElement;
    private String detectorName;
    private String details;
    private String commit;
    private Metrics metrics;


    public CodeSmell(String smellId,
                     String name,
                     String projectName,
                     String codeElement,
                     String commit,
                     String detectorName,
                     String details,
                     Metrics metrics) {
        this.smellId = smellId;
        this.name = name;
        this.projectName = projectName;
        this.codeElement = codeElement;
        this.commit = commit;
        this.detectorName = detectorName;
        this.details = details;
        this.metrics = metrics;
    }

    public CodeSmell(){

    }

    public String getSmellId() {
        return smellId;
    }

    public void setSmellId(String smellId) {
        this.smellId = smellId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCodeElement() {
        return codeElement;
    }

    public void setCodeElement(String codeElement) {
        this.codeElement = codeElement;
    }

    public String getDetectorName() {
        return detectorName;
    }

    public void setDetectorName(String detectorName) {
        this.detectorName = detectorName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }
}
