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
        catalogAnalyzer.getCatalogFirstRecommendationResult();

    }

    private void getCatalogFirstRecommendationResult(){

        List<CompositeGroup> summarizedGroups = groupAnalyzer.getCompositeGroupFromJson("summarized-groups.json");

        List<String> groupNames = new ArrayList<>();
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, RENAME]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, REFACT_VARIABLE]");
        groupNames.add("[EXTRACT_METHOD, MOVE_METHOD, REFACT_VARIABLE, RENAME]");
        List<CompositeEffectDTO> composites = groupAnalyzer.gettAllCompositesFromSpecificGroups(groupNames, summarizedGroups);

        List<String> existingSmells = new ArrayList<>();
        existingSmells.add("FeatureEnvy");
        existingSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithExistingSmells = effectAnalyzer.getCompositesThatHaveSpecificSmellTypes(existingSmells, composites);


        List<String> removedSmells = new ArrayList<>();
        removedSmells.add("FeatureEnvy");
        removedSmells.add("LongMethod");
        List<CompositeEffectDTO> compositesWithRemovedSmells = effectAnalyzer.getCompositesWithSmellTypesRemoval(removedSmells, compositesWithExistingSmells);

        List<CompositeEffectDTO> specificComposites = compositesWithRemovedSmells;

        //Composites with side effects
        List<String> addedSmells = new ArrayList<>();
        addedSmells.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithAddedSmells = effectAnalyzer.getCompositesWithSmellTypesAddition(addedSmells, specificComposites);

        //Composites without side effects - composites that follow the recommendation
        List<String> smellsTotallyRemoved = new ArrayList<>();
        smellsTotallyRemoved.add("FeatureEnvy");
        List<CompositeEffectDTO> compositesWithTotalRemovalOfSmell = effectAnalyzer.getCompositesWithTotalSmellTypesRemoval(smellsTotallyRemoved, specificComposites);


    }
}
