package inf.puc.rio.opus.composite.anaysis.analysis;

import inf.puc.rio.opus.composite.model.effect.CompositeEffectDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;

import java.util.ArrayList;
import java.util.List;

public class CompositeCatalogAnalyzer {


    CompositeEffectAnalyzer effectAnalyzer = new CompositeEffectAnalyzer();
    CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();
    CompositeAnalyzer compositeAnalyzer = new CompositeAnalyzer();

    public static void main(String[] args) {

        CompositeCatalogAnalyzer catalogAnalyzer = new CompositeCatalogAnalyzer();
        //  catalogAnalyzer.getCatalogFirstRecommendationResult();
        catalogAnalyzer.getCatalogSecondRecommendationResult();
        // catalogAnalyzer.getCatalogThirdRecommendationResult();
        // catalogAnalyzer.getCatalogFourthRecommendationResult();
       // catalogAnalyzer.getCatalogFifthRecommendationResult();
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
        composites.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);
        System.out.println("Composites with Removed Smells: ");
        compositesWithRemovedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();
        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;
        System.out.println("Total: " + specificComposites.size() + " composites");

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() +", "));
        System.out.println();
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
        composites.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);
        System.out.println("Composites with Removed Smells: ");
        compositesWithRemovedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;
        System.out.println();
        System.out.println("Total: " + specificComposites.size() + " composites");

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");

    }

    private void getCatalogThirdRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");
        System.out.println("RESULT OF THIRD RECOMMENDATION OF CATALOG");

        List<String> groupNames = new ArrayList<>();
        groupNames.add("[EXTRACT_METHOD]");
        groupNames.add("[EXTRACT_METHOD, RENAME]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);
        System.out.println("Number of Composites: " + composites.size() + " composites");
        composites.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");
        List<CompositeEffectDTO> specificComposites = compositesWithExistingSmells;

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("LongMethod");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");

    }

    private void getCatalogFourthRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");
        System.out.println("RESULT OF Fourth RECOMMENDATION OF CATALOG");

        List<String> groupNames = new ArrayList<>();
        groupNames.add("[EXTRACT_METHOD]");
        groupNames.add("[EXTRACT_METHOD, RENAME]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE]");
        groupNames.add("[EXTRACT_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);
        System.out.println("Number of Composites: " + composites.size() + " composites");
        composites.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);
        System.out.println("Composites with Removed Smells: ");
        compositesWithRemovedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;
        System.out.println();

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("LongParameterList");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("LongParameterList");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");

    }


    private void getCatalogFifthRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");
        System.out.println("RESULT OF FIFTH RECOMMENDATION OF CATALOG");

        List<String> groupNames = new ArrayList<>();
        groupNames.add("[MOVE_METHOD]");
        groupNames.add("[MOVE_METHOD, RENAME]");
        groupNames.add("[MOVE_METHOD, REFACT_VARIABLE]");
        groupNames.add("[MOVE_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);
        System.out.println("Number of Composites: " + composites.size() + " composites");
        composites.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId() + ", "));
        System.out.println();

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);
        System.out.println("Composites with Existing Smells: ");
        compositesWithExistingSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithExistingSmells.size() + " composites");

        List<CompositeEffectDTO> specificComposites = compositesWithExistingSmells;
        System.out.println("Total: " + specificComposites.size() + " composites");

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);
        System.out.println("Composites with Side Effects (Added Smells): ");
        compositesWithAddedSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithAddedSmells.size() + " composites");

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmells = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);
        System.out.println("Composites without Side Effects (Recommended Composites): ");
        compositesWithTotalRemovalOfSmells.forEach(composite -> System.out.print(composite.getProject()+"-"+composite.getId()+ ", "));
        System.out.println();
        System.out.println("Total: " + compositesWithTotalRemovalOfSmells.size() + " composites");
    }
}
