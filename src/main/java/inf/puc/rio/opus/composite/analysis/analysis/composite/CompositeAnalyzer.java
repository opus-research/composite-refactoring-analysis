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

    public CompositeAnalyzer() {
        this.groupAnalyzer = new CompositeGroupAnalyzer();
    }

    public static void main(String[] args) {

        CompositeAnalyzer analyzer = new CompositeAnalyzer();
        analyzer.collector = new CompositeCollector(args);

        String compositeIds = "ant-582, spymemcached-75, ant-1159, junit4-302, spymemcached-14, ant-2991, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, ant-850, ant-2554, spymemcached-132, ant-1865, ant-1145, ant-1019, ant-1099\n" +
                ",ant-606, ant-1417, ant-3125, ant-1148, deltachat-android-101, leakcanary-36, ant-2098, ant-732, " +
                "ant-1264, ant-586, genie-1115, ant-593, ant-1456, ant-728, ant-1607, spymemcached-8, ant-597, ant-1163, " +
                "ant-1138, ant-285, genie-1489, genie-1240, spymemcached-150, genie-1518";

        List<CompositeDTO> composites = analyzer.collector.getCompositesByIds(compositeIds);

        Map<String, List<CompositeDTO>> groups = analyzer.groupAnalyzer.createCompositeGroupsFromRefactoringAsList(composites);

        List<CompositeGroup> groupList = analyzer.groupAnalyzer.summarizeGroupSet(groups);

        System.out.println(groupList.get(2).getGroupSet());
        CompositeUtils.generateCombinations(groupList.get(2).getGroupSet(),2);

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
