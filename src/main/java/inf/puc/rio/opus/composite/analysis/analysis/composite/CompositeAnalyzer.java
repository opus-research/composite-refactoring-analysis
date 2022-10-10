package inf.puc.rio.opus.composite.analysis.analysis.composite;

import com.fasterxml.jackson.databind.ObjectMapper;
import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.database.composites.CompositeCollector;
import inf.puc.rio.opus.composite.model.refactoring.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;

import java.io.File;
import java.io.IOException;
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

        String compositeIds = "";

        compositeIds += "";
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
                        compositeID = compositeID.trim();
                        if(compositeIDFormatted.equals(compositeID)) {

                            for (CompositeDTO compositesDTOWithRefactoring : compositesDTOWithRefactorings) {

                                if (compositesDTOWithRefactoring.getId().equals(compositeID)) {
                                    compositeDTO.setRefactoringsList(compositesDTOWithRefactoring.getRefactoringsList());
                                }
                            }
                        }
                        compositeDTOS.add(compositeDTO);
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
