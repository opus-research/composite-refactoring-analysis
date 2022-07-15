package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.database.composites.CompositeCollector;
import inf.puc.rio.opus.composite.database.composites.CompositeRepository;
import inf.puc.rio.opus.composite.database.refactorings.RefactoringRepository;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Ref;
import java.util.*;
import java.util.List;

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

        List<CompositeGroup> summarizedGroups =  analyzer.groupAnalyzer.getCompositeGroupFromJson("");

        List<String> compositeIDs = new ArrayList<>();
        List<CompositeDTO> composites = analyzer.getCompositesByIdsFromGroup(compositeIDs, summarizedGroups);
        analyzer.effectAnalyzer.writeCompositeEffectAsCSV(composites);

    }

    private List<CompositeDTO> getCompositesByIdsFromGroup(List<String> compositeIDs, List<CompositeGroup> summarizedGroups) {

        Set<CompositeDTO> compositeDTOS = new HashSet<>();
        List<CompositeDTO> compositesDTOWithRefactorings = collector.getCompositesByIds(compositeIDs.toString());
        for (CompositeGroup summarizedGroup : summarizedGroups) {

            for (CompositeDTO compositeDTO : summarizedGroup.getComposites()) {

                for (String compositeID : compositeIDs) {

                    if(compositeDTO.getId().equals(compositeID)){
                        CompositeDTO compositeWithRefs = compositesDTOWithRefactorings.stream()
                                .filter(comp -> comp.getId().equals(compositeID))
                                .findFirst()
                                .get();

                        compositeDTO.setRefactoringsList(compositeWithRefs.getRefactoringsList());
                        compositeDTOS.add(compositeDTO);
                    }
                }
            }
        }

        return new ArrayList<>(compositeDTOS);
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
