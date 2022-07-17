package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
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

        String compositeIds = "ant-1116, okhttp-1346, ant-732, ant-593, spymemcached-8, dubbo-775, jgit-609, jgit-657, jgit-840, dubbo-787, dubbo-709, okhttp-730, okhttp-921, jgit-820, jgit-132, jgit-691," +
                "ant-732, ant-593, spymemcached-8, dubbo-775, jgit-609, jgit-657, jgit-840, dubbo-787, dubbo-709, okhttp-730, okhttp-921, jgit-820, jgit-132, jgit-691, ant-1153, ant-1019, ant-597, ant-1138, " +
                "jgit-1379, dubbo-481, ant-560, ant-606, ant-1148, genie-1115, dubbo-726, dubbo-773, fresco-159, fresco-158, jgit-657, jgit-309, jgit-800, jgit-840, okhttp-730, jgit-1446, jgit-132, fresco-724, " +
                "couchbase-java-client-260, jgit-1617, fresco-128, jgit-168";

        List<CompositeGroup> summarizedGroups =  analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-groups-dataset1.json");
        summarizedGroups.addAll(analyzer.groupAnalyzer.getCompositeGroupFromJson("summarized-groups-dataset2.json"));


        List<CompositeDTO> composites = analyzer.getCompositesByIdsFromGroup(compositeIds, summarizedGroups);
        analyzer.effectAnalyzer.writeCompositeEffectAsCSV(composites);

    }

    private List<CompositeDTO> getCompositesByIdsFromGroup(String compositeIDs, List<CompositeGroup> summarizedGroups) {

        Set<CompositeDTO> compositeDTOS = new HashSet<>();
        List<String> compositeIDList = CompositeUtils.convertTextToList(compositeIDs);
        Set<String> compositesSet = new HashSet<>(compositeIDList);

        compositeIDList = new ArrayList<>(compositesSet);

        List<CompositeDTO> compositesDTOWithRefactorings = collector.getCompositesByIdsWithRefsObj(compositeIDs);
        for (CompositeGroup summarizedGroup : summarizedGroups) {

            for (CompositeDTO compositeDTO : summarizedGroup.getComposites()) {

                for (String compositeID : compositeIDList) {

                    String compositeIDFormatted = compositeDTO.getProject() + "-" + compositeDTO.getId();
                    if(compositeIDFormatted.equals(compositeID)){
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
