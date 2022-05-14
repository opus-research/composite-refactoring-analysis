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
    public CompositeCatalogAnalyzer catalogAnalyzer;

    public CompositeAnalyzer() {
        this.groupAnalyzer = new CompositeGroupAnalyzer();
        this.effectAnalyzer = new CompositeEffectAnalyzer();
        this.catalogAnalyzer = new CompositeCatalogAnalyzer();
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
        List<CompositeGroup> remainingSummarizedGroups = new ArrayList<>();

        // Procedure 2 - Collect composite ids for each scenario of catalog from remaining projects
        // catalogAnalyzer.getCatalogFirstRecommendationResult (summarized-remaining-projects.json)
        // add composite ids in catalog table
        List<CompositeGroup> summarizedGroupDubbo = analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-group-dubbo.json");
        List<CompositeGroup> summarizedGroupOkhttp = analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-group-okhttp.json");
        List<CompositeGroup> summarizedGroupCouchbase = analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-group-couchbase-java-client.json");
        List<CompositeGroup> summarizedGroupFresco = analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-group-fresco.json");
        List<CompositeGroup> summarizedGroupJgit = analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-group-jgit.json");

        remainingSummarizedGroups.addAll(summarizedGroupDubbo);
        remainingSummarizedGroups.addAll(summarizedGroupOkhttp);
        remainingSummarizedGroups.addAll(summarizedGroupCouchbase);
        remainingSummarizedGroups.addAll(summarizedGroupFresco);
        remainingSummarizedGroups.addAll(summarizedGroupJgit);

        analyzer.groupAnalyzer.writeSummarizedGroupAsJson(remainingSummarizedGroups, "remaining-summarized-group.json");

        // analyzer.catalogAnalyzer.getCatalogFirstRecommendationResult();
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
