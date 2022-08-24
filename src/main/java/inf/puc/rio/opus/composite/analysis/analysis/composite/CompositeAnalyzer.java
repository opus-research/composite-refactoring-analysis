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
                "couchbase-java-client-260, jgit-1617, fresco-128, jgit-168,";

        compositeIds += "ant-2963, ant-690, ant-1076, ant-884, ant-1116, okhttp-1346, fresco-557," +
                "ant-582, ant-1159, ant-299, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, ant-850, spymemcached-132, ant-1019, " +
                "ant-1099, ant-606, ant-1417, ant-3125, deltachat-android-101, ant-732, ant-1264, ant-593,spymemcached-8, ant-597, ant-1163, " +
                "ant-1138, genie-1489, genie-1240, dubbo-775, dubbo-726, okhttp-1228, okhttp-243, fresco-505, jgit-1586, jgit-506, jgit-609," +
                "dubbo-773, dubbo-77, okhttp-825, fresco-393, fresco-159, fresco-158, fresco-687, jgit-810, jgit-445, jgit-657, jgit-309, dubbo-701, " +
                "okhttp-889, okhttp-1253, jgit-800, jgit-1379, jgit-840, jgit-444, jgit-213, dubbo-704, dubbo-787, dubbo-708, dubbo-2968, dubbo-481, " +
                "dubbo-855, dubbo-709, okhttp-15, okhttp-730, okhttp-921, fresco-510, jgit-820, jgit-793, jgit-501, jgit-474, jgit-708, jgit-132, " +
                "jgit-1428, jgit-691,ant-582, ant-1159, ant-299, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, ant-850, spymemcached-132, " +
                "ant-1019, ant-1099, ant-606, ant-1417, ant-3125, deltachat-android-101, ant-732, ant-1264, ant-593, spymemcached-8, ant-597, ant-1163, " +
                "ant-1138, genie-1489, genie-1240, dubbo-775, dubbo-726, okhttp-1228, okhttp-243, fresco-505, jgit-1586, jgit-506, jgit-609, dubbo-773, " +
                "dubbo-77, okhttp-825, fresco-393, fresco-159, fresco-158, fresco-687, jgit-810, jgit-445, jgit-657, jgit-309, dubbo-701, okhttp-889, " +
                "okhttp-1253, jgit-800, jgit-1379, jgit-840, jgit-444, jgit-213, dubbo-704, dubbo-787, dubbo-708, dubbo-2968, dubbo-481, dubbo-855, dubbo-709, " +
                "okhttp-15, okhttp-730, okhttp-921, fresco-510, jgit-820, jgit-793, jgit-501, jgit-474, jgit-708, jgit-132, jgit-1428, jgit-691," +
                "ant-582, spymemcached-75, ant-1159, junit4-302, spymemcached-14, ant-2991, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, " +
                "ant-850, ant-2554, spymemcached-132, ant-1865, ant-1145, ant-1019, ant-1099," +
                "ant-606, ant-1417, ant-3125, ant-1148, deltachat-android-101, leakcanary-36, ant-2098, " +
                "ant-732, ant-1264, ant-586, genie-1115, ant-593, ant-1456, ant-728, ant-1607, spymemcached-8, " +
                "ant-597, ant-1163, ant-1138, ant-285, genie-1489, genie-1240, spymemcached-150, genie-1518, dubbo-775, " +
                "dubbo-726, okhttp-1228, okhttp-243, fresco-505, jgit-1586, jgit-506, jgit-609, dubbo-773, dubbo-77, okhttp-825, " +
                "couchbase-java-client-313, fresco-393, fresco-159, fresco-158, fresco-687, fresco-463, jgit-810, jgit-445, jgit-657, " +
                "jgit-309, dubbo-701, okhttp-889, okhttp-1253, jgit-800, jgit-1379, jgit-840, jgit-444, jgit-213, jgit-101, dubbo-704, " +
                "dubbo-787, dubbo-708, dubbo-2968, dubbo-481, dubbo-855, dubbo-709, okhttp-15, okhttp-730, okhttp-921, fresco-510, jgit-820, jgit-1446, jgit-793, " +
                "jgit-501, jgit-474, jgit-708, jgit-132, jgit-1428, jgit-691," +
                "ant-582, spymemcached-75, ant-1159, junit4-302, spymemcached-14, ant-2991, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, ant-850, ant-2554, " +
                "spymemcached-132, ant-1865, ant-1145, ant-1019, ant-1099," +
                "ant-606, ant-1417, ant-3125, ant-1148, deltachat-android-101, leakcanary-36, ant-2098, ant-732, ant-1264, ant-586, genie-1115, ant-593, ant-1456, " +
                "ant-728, ant-1607, spymemcached-8, ant-597, ant-1163, ant-1138, ant-285, genie-1489, genie-1240, spymemcached-150, genie-1518, dubbo-775, dubbo-726, " +
                "okhttp-1228, okhttp-243, fresco-505, jgit-1586, jgit-506, jgit-609, dubbo-773, dubbo-77, okhttp-825, couchbase-java-client-313, fresco-393, " +
                "fresco-159, fresco-158, fresco-687, fresco-463, jgit-810, jgit-445, jgit-657, jgit-309, dubbo-701, okhttp-889, okhttp-1253, jgit-800, jgit-1379, " +
                "jgit-840, jgit-444, jgit-213, jgit-101, dubbo-704, dubbo-787, dubbo-708, dubbo-2968, dubbo-481, dubbo-855, dubbo-709, okhttp-15, okhttp-730, " +
                "okhttp-921, fresco-510, jgit-820, jgit-1446, jgit-793, jgit-501, jgit-474, jgit-708, jgit-132, jgit-1428, jgit-691," +
                "ant-1882, ant-1862, jfreechart-346, dubbo-475, fresco-698, fresco-724, dubbo-143, couchbase-java-client-260, jgit-1617, " +
                "jgit-1155, dubbo-2718, okhttp-695, fresco-128, jgit-1322, jgit-168, jgit-1094";
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
