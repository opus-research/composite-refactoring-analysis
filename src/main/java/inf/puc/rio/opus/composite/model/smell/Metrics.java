
package inf.puc.rio.opus.composite.model.smell;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

        "CLOC",
        "ClassLinesOfCode",
        "MLOC",
        "MethodLinesOfCode",
        "CC",
        "CyclomaticComplexity",
        "IsAbstract",
        "MaxCallChain",
        "ParameterCount",
        "OverrideRatio",
        "PublicFieldCount",
        "TCC",
        "TightClassCohesion",
        "MaxNesting",
        "NOAV",
        "NumberOfAccessedVariables",
        "NOAM",
        "NumberOfAccessorMethods",
        "WMC",
        "WeightedMethodCount",
        "WOC",
        "WeighOfClass",
        "CINT",
        "CouplingIntensity",
        "CDISP",
        "CouplingDispersion",
        "ChangingClasses",
        "ChangingMethods",
        "LCOM",
        "LackOfCohesionOfMethods",
        "LCOM2",
        "LCOM3"
})
public class Metrics {


    @JsonProperty("CLOC")
    private float CLOC;

    @JsonProperty("ClassLinesOfCode")
    private float ClassLinesOfCode;

    @JsonProperty("MLOC")
    private float MLOC;

    @JsonProperty("MethodLinesOfCode")
    private float MethodLinesOfCode;

    @JsonProperty("CC")
    private float CC;

    @JsonProperty("CyclomaticComplexity")
    private float CyclomaticComplexity;

    @JsonProperty("IsAbstract")
    private float IsAbstract;

    @JsonProperty("MaxCallChain")
    private float MaxCallChain;

    @JsonProperty("ParameterCount")
    private float ParameterCount;

    @JsonProperty("OverrideRatio")
    private float OverrideRatio;

    @JsonProperty("PublicFieldCount")
    private float PublicFieldCount;

    @JsonProperty("TCC")
    private float TCC;

    @JsonProperty("TightClassCohesion")
    private float TightClassCohesion;

    @JsonProperty("MaxNesting")
    private float MaxNesting;

    @JsonProperty("NOAV")
    private float NOAV;

    @JsonProperty("NumberOfAccessedVariables")
    private float NumberOfAccessedVariables;

    @JsonProperty("NOAM")
    private float NOAM;

    @JsonProperty("NumberOfAccessorMethods")
    private float NumberOfAccessorMethods;

    @JsonProperty("WMC")
    private float WMC;

    @JsonProperty("WeightedMethodCount")
    private float WeightedMethodCount;

    @JsonProperty("WOC")
    private float WOC;

    @JsonProperty("WeighOfClass")
    private float WeighOfClass;

    @JsonProperty("CINT")
    private float CINT;

    @JsonProperty("CouplingIntensity")
    private float CouplingIntensity;

    @JsonProperty("CDISP")
    private float CDISP;

    @JsonProperty("CouplingDispersion")
    private float CouplingDispersion;

    @JsonProperty("ChangingClasses")
    private float ChangingClasses;

    @JsonProperty("ChangingMethods")
    private float ChangingMethods;

    @JsonProperty("LCOM")
    private float LCOM;

    @JsonProperty("LackOfCohesionOfMethods")
    private float LackOfCohesionOfMethods;

    @JsonProperty("LCOM2")
    private float LCOM2;

    @JsonProperty("LCOM3")
    private float LCOM3;


    @JsonProperty
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("CLOC")
    public float getCLOC() {
        return CLOC;
    }

    @JsonProperty("CLOC")
    public void setCLOC(float CLOC) {
        this.CLOC = CLOC;
    }

    @JsonProperty("ClassLinesOfCode")
    public float getClassLinesOfCode() {
        return ClassLinesOfCode;
    }

    @JsonProperty("ClassLinesOfCode")
    public void setClassLinesOfCode(float classLinesOfCode) {
        ClassLinesOfCode = classLinesOfCode;
    }

    @JsonProperty("MLOC")
    public float getMLOC() {
        return MLOC;
    }

    @JsonProperty("MLOC")
    public void setMLOC(float MLOC) {
        this.MLOC = MLOC;
    }

    @JsonProperty("MethodLinesOfCode")
    public float getMethodLinesOfCode() {
        return MethodLinesOfCode;
    }

    @JsonProperty("MethodLinesOfCode")
    public void setMethodLinesOfCode(float methodLinesOfCode) {
        MethodLinesOfCode = methodLinesOfCode;
    }

    @JsonProperty("CC")
    public float getCC() {
        return CC;
    }

    @JsonProperty("CC")
    public void setCC(float CC) {
        this.CC = CC;
    }

    @JsonProperty("CyclomaticComplexity")
    public float getCyclomaticComplexity() {
        return CyclomaticComplexity;
    }

    @JsonProperty("CyclomaticComplexity")
    public void setCyclomaticComplexity(float cyclomaticComplexity) {
        CyclomaticComplexity = cyclomaticComplexity;
    }

    @JsonProperty("IsAbstract")
    public float getIsAbstract() {
        return IsAbstract;
    }

    @JsonProperty("IsAbstract")
    public void setIsAbstract(float isAbstract) {
        IsAbstract = isAbstract;
    }

    @JsonProperty("MaxCallChain")
    public float getMaxCallChain() {
        return MaxCallChain;
    }

    @JsonProperty("MaxCallChain")
    public void setMaxCallChain(float maxCallChain) {
        MaxCallChain = maxCallChain;
    }

    @JsonProperty("ParameterCount")
    public float getParameterCount() {
        return ParameterCount;
    }

    @JsonProperty("ParameterCount")
    public void setParameterCount(float parameterCount) {
        ParameterCount = parameterCount;
    }

    @JsonProperty("OverrideRatio")
    public float getOverrideRatio() {
        return OverrideRatio;
    }

    @JsonProperty("OverrideRatio")
    public void setOverrideRatio(float overrideRatio) {
        OverrideRatio = overrideRatio;
    }

    @JsonProperty("PublicFieldCount")
    public float getPublicFieldCount() {
        return PublicFieldCount;
    }

    @JsonProperty("PublicFieldCount")
    public void setPublicFieldCount(float publicFieldCount) {
        PublicFieldCount = publicFieldCount;
    }

    @JsonProperty("TCC")
    public float getTCC() {
        return TCC;
    }

    @JsonProperty("TCC")
    public void setTCC(float TCC) {
        this.TCC = TCC;
    }

    @JsonProperty("TightClassCohesion")
    public float getTightClassCohesion() {
        return TightClassCohesion;
    }

    @JsonProperty("TightClassCohesion")
    public void setTightClassCohesion(float tightClassCohesion) {
        TightClassCohesion = tightClassCohesion;
    }

    @JsonProperty("MaxNesting")
    public float getMaxNesting() {
        return MaxNesting;
    }

    @JsonProperty("MaxNesting")
    public void setMaxNesting(float maxNesting) {
        MaxNesting = maxNesting;
    }

    @JsonProperty("NOAV")
    public float getNOAV() {
        return NOAV;
    }

    @JsonProperty("NOAV")
    public void setNOAV(float NOAV) {
        this.NOAV = NOAV;
    }

    @JsonProperty("NumberOfAccessedVariables")
    public float getNumberOfAccessedVariables() {
        return NumberOfAccessedVariables;
    }

    @JsonProperty("NumberOfAccessedVariables")
    public void setNumberOfAccessedVariables(float numberOfAccessedVariables) {
        NumberOfAccessedVariables = numberOfAccessedVariables;
    }

    @JsonProperty("NOAM")
    public float getNOAM() {
        return NOAM;
    }

    @JsonProperty("NOAM")
    public void setNOAM(float NOAM) {
        this.NOAM = NOAM;
    }

    @JsonProperty("NumberOfAccessorMethods")
    public float getNumberOfAccessorMethods() {
        return NumberOfAccessorMethods;
    }

    @JsonProperty("NumberOfAccessorMethods")
    public void setNumberOfAccessorMethods(float numberOfAccessorMethods) {
        NumberOfAccessorMethods = numberOfAccessorMethods;
    }

    @JsonProperty("WMC")
    public float getWMC() {
        return WMC;
    }

    @JsonProperty("WMC")
    public void setWMC(float WMC) {
        this.WMC = WMC;
    }

    @JsonProperty("WeightedMethodCount")
    public float getWeightedMethodCount() {
        return WeightedMethodCount;
    }

    @JsonProperty("WeightedMethodCount")
    public void setWeightedMethodCount(float weightedMethodCount) {
        WeightedMethodCount = weightedMethodCount;
    }

    @JsonProperty("WOC")
    public float getWOC() {
        return WOC;
    }

    @JsonProperty("WOC")
    public void setWOC(float WOC) {
        this.WOC = WOC;
    }

    @JsonProperty("WeighOfClass")
    public float getWeighOfClass() {
        return WeighOfClass;
    }

    @JsonProperty("WeighOfClass")
    public void setWeighOfClass(float weighOfClass) {
        WeighOfClass = weighOfClass;
    }

    @JsonProperty("CINT")
    public float getCINT() {
        return CINT;
    }

    @JsonProperty("CINT")
    public void setCINT(float CINT) {
        this.CINT = CINT;
    }

    @JsonProperty("CouplingIntensity")
    public float getCouplingIntensity() {
        return CouplingIntensity;
    }

    @JsonProperty("CouplingIntensity")
    public void setCouplingIntensity(float couplingIntensity) {
        CouplingIntensity = couplingIntensity;
    }

    @JsonProperty("CDISP")
    public float getCDISP() {
        return CDISP;
    }

    @JsonProperty("CDISP")
    public void setCDISP(float CDISP) {
        this.CDISP = CDISP;
    }

    @JsonProperty("CouplingDispersion")
    public float getCouplingDispersion() {
        return CouplingDispersion;
    }

    @JsonProperty("CouplingDispersion")
    public void setCouplingDispersion(float couplingDispersion) {
        CouplingDispersion = couplingDispersion;
    }

    @JsonProperty("ChangingClasses")
    public float getChangingClasses() {
        return ChangingClasses;
    }

    @JsonProperty("ChangingClasses")
    public void setChangingClasses(float changingClasses) {
        ChangingClasses = changingClasses;
    }

    @JsonProperty("ChangingMethods")
    public float getChangingMethods() {
        return ChangingMethods;
    }

    @JsonProperty("ChangingMethods")
    public void setChangingMethods(float changingMethods) {
        ChangingMethods = changingMethods;
    }

    @JsonProperty("LCOM")
    public float getLCOM() {
        return LCOM;
    }

    @JsonProperty("LCOM")
    public void setLCOM(float LCOM) {
        this.LCOM = LCOM;
    }

    @JsonProperty("LackOfCohesionOfMethods")
    public float getLackOfCohesionOfMethods() {
        return LackOfCohesionOfMethods;
    }

    @JsonProperty("LackOfCohesionOfMethods")
    public void setLackOfCohesionOfMethods(float lackOfCohesionOfMethods) {
        LackOfCohesionOfMethods = lackOfCohesionOfMethods;
    }

    @JsonProperty("LCOM2")
    public float getLCOM2() {
        return LCOM2;
    }

    @JsonProperty("LCOM2")
    public void setLCOM2(float LCOM2) {
        this.LCOM2 = LCOM2;
    }

    @JsonProperty("LCOM3")
    public float getLCOM3() {
        return LCOM3;
    }

    @JsonProperty("LCOM3")
    public void setLCOM3(float LCOM3) {
        this.LCOM3 = LCOM3;
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
