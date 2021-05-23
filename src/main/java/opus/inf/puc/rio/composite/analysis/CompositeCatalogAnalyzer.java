package opus.inf.puc.rio.composite.analysis;

import inf.puc.rio.opus.composite.model.CompositeEffectDTO;
import inf.puc.rio.opus.composite.model.CompositeGroup;

import java.util.ArrayList;
import java.util.List;

public class CompositeCatalogAnalyzer {


    CompositeEffectAnalyzer effectAnalyzer = new CompositeEffectAnalyzer();
    CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();
    CompositeAnalyzer compositeAnalyzer = new CompositeAnalyzer();

    public static void main(String[] args) {

        CompositeCatalogAnalyzer catalogAnalyzer = new CompositeCatalogAnalyzer();
        //catalogAnalyzer.getCatalogFirstRecommendationResult();
        catalogAnalyzer.getCatalogSecondRecommendationResult();
    }

    private void getCatalogFirstRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");

        System.out.println("RESULT OF FIRST RECOMMENDATION OF CATALOG");
        List<String> groupNames = new ArrayList<>();
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, RENAME]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, REFACT_VARIABLE]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);
        System.out.println("Number of Composites: " + composites.size() + " composites");

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);
        System.out.println("Composites with Removed Smells: ");
        compositesWithRemovedSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;
        System.out.println("Total: " + specificComposites.size() + " composites");

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");

    }

    private void getCatalogSecondRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");
        System.out.println("RESULT OF SECOND RECOMMENDATION OF CATALOG");

        List<String> groupNames = new ArrayList<>();
        groupNames.add("[EXTRACT_METHOD]");
        groupNames.add("[EXTRACT_METHOD, RENAME]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);
        System.out.println("Number of Composites: " + composites.size() + " composites");

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);
        System.out.println("Composites with Removed Smells: ");
        compositesWithRemovedSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;
        System.out.println("Total: " + specificComposites.size() + " composites");

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.println(composite.getProject()+"-"+composite.getId()));
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");

    }
}
