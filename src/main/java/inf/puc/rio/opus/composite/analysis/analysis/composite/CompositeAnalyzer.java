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


        String compositeIds = "ant-1882, ant-1862, jfreechart-346, dubbo-475, fresco-698, fresco-724, dubbo-143, couchbase-java-client-260, jgit-1617, jgit-1155, dubbo-2718, okhttp-695, fresco-128, jgit-1322, jgit-168, jgit-1094";
        List<CompositeDTO> composites = analyzer.collector.getCompositesByIds(compositeIds);

        Map<String, List<CompositeDTO>> groups = analyzer.groupAnalyzer.createCompositeGroupsFromRefactoringAsList(composites);

        List<CompositeGroup> groupList = analyzer.groupAnalyzer.summarizeGroupSet(groups);

        Map<String, Integer> rank = analyzer.groupAnalyzer.rankGroupCombinations(groupList);

        analyzer.groupAnalyzer.writeRankOfCompositeGroup(rank, "S5.csv");

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
