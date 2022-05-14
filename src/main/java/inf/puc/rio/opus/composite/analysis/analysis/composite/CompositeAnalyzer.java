package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.database.composites.CompositeCollector;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CompositeAnalyzer {

    public CompositeCollector collector;
    public CompositeGroupAnalyzer groupAnalyzer;
    public CompositeEffectAnalyzer effectAnalyzer;

    public CompositeAnalyzer() {
        this.groupAnalyzer = new CompositeGroupAnalyzer();
        this.effectAnalyzer = new CompositeEffectAnalyzer();
    }

   /*
   * Ver quais complete composites dos projetos restantes se enquadram nos 5 cenarios do Catalogo
   * Para isso precisa-se primeiro gerar um summarized-groups no formato antigo para esses projetos
   * a partir dos CSV
   * */

    public static void main(String[] args) {

        CompositeAnalyzer analyzer = new CompositeAnalyzer();
        analyzer.collector = new CompositeCollector(args);

        String projectName = "okhttp";
        // Procedure 1 - Collect summarized groups of remaining projects
        // Get Composite and Smells data from CSV
        List<CompositeDTO> compositeDTOList = analyzer.effectAnalyzer.getCompositeEffectDTO("effect-composites-" + projectName +".csv");


        // Get Composite Effect from composite dto list
        compositeDTOList  = analyzer.effectAnalyzer.getCompleteComposite(compositeDTOList);
        analyzer.effectAnalyzer.writeCompositeEffectAsJson( compositeDTOList, "composite-effect-"+ projectName + ".json");

        // Get groups
        Map<String, List<CompositeDTO>> groups = analyzer.groupAnalyzer.createCompositeGroups(compositeDTOList);
        analyzer.groupAnalyzer.writeCompositeGroupAsJson(groups, "composite-group-"+ projectName +".json");

        // Get summarized groups
        List<CompositeGroup> summarizedGroups = analyzer.groupAnalyzer.summarizeGroups(groups);
        analyzer.groupAnalyzer.writeSummarizedGroupAsJson(summarizedGroups, "summarized-group-"+ projectName +".json");


        // Procedure 2 - Collect composite ids for each scenario of catalog from remaining projects
        // catalogAnalyzer.getCatalogFirstRecommendationResult (summarized-remaining-projects.json)
        // add composite ids in catalog table

        // Procedure 3
        // Collect ranking for each scenario in catalog like other time
    }




    public List<CompositeRefactoring> getCompositeFromJson(String compositePath)
    {
        ObjectMapper mapper = new ObjectMapper();
        List<CompositeRefactoring> compositeList = new ArrayList<>();
        try {

            CompositeRefactoring[] composites = mapper.readValue(new File(compositePath),
                    CompositeRefactoring[].class);

            compositeList = new ArrayList<CompositeRefactoring>(Arrays.asList(composites));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return compositeList;
    }
}
