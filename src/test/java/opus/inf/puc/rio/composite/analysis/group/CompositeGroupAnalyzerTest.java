package opus.inf.puc.rio.composite.analysis.group;

import inf.puc.rio.opus.composite.analysis.analysis.composite.CompositeGroupAnalyzer;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;
import inf.puc.rio.opus.composite.model.group.CompositeGroup;
import org.junit.Test;

import java.util.*;

public class CompositeGroupAnalyzerTest {


    @Test
    public void rankCombinationsTest(){


        CompositeGroupAnalyzer groupAnalyzer = new CompositeGroupAnalyzer();

        List<CompositeGroup> groups = new ArrayList<CompositeGroup>();

        Set<String> composite1 = new HashSet<>();
        composite1.add("RENAME");
        composite1.add("EXTRACT_METHOD");
        composite1.add("MOVE_METHOD");
        composite1.add("CHANGE_VARIABLE_TYPE");
        List<CompositeDTO> compositeDTOs1 = new ArrayList<>();
        compositeDTOs1.add(new CompositeDTO());
        compositeDTOs1.add(new CompositeDTO());
        compositeDTOs1.add(new CompositeDTO());


        CompositeGroup group1 = new CompositeGroup(composite1, compositeDTOs1, "", "");
        groups.add(group1);


        Set<String> composite2 = new HashSet<>();
        composite2.add("EXTRACT_METHOD");
        composite2.add("MOVE_METHOD");
        composite2.add("CHANGE_VARIABLE_TYPE");
        List<CompositeDTO> compositeDTOs2 = new ArrayList<>();
        compositeDTOs2.add(new CompositeDTO());
        compositeDTOs2.add(new CompositeDTO());

        CompositeGroup group2 = new CompositeGroup(composite2, compositeDTOs2, "", "");
        groups.add(group2);


        Map<String, Integer> rank = groupAnalyzer.rankGroupCombinations(groups);
        System.out.println(rank);


    }
}
