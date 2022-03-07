package inf.puc.rio.opus.composite.database.composites;

import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.database.refactorings.RefactoringRepository;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeCollector {

    private CompositeRepository compositeRepository;
    private RefactoringRepository refactoringRepository;

    private CompositeCollector(String[] args){
        compositeRepository= new CompositeRepository(args);
        refactoringRepository= new RefactoringRepository(args);
    }

    public static void main(String[] args) {
        CompositeCollector collector = new CompositeCollector(args);

        // List<CompositeRefactoring> composites = collector.getAllComposites();

        String compositeIds = "ant-582, spymemcached-75, ant-1159, junit4-302, spymemcached-14, ant-2991, ant-644, ant-1153, ant-560, ant-1268, ant-1144, ant-1373, ant-850, ant-2554, spymemcached-132, ant-1865, ant-1145, ant-1019, ant-1099\n" +
                ",ant-606, ant-1417, ant-3125, ant-1148, deltachat-android-101, leakcanary-36, ant-2098, ant-732, " +
                "ant-1264, ant-586, genie-1115, ant-593, ant-1456, ant-728, ant-1607, spymemcached-8, ant-597, ant-1163, " +
                "ant-1138, ant-285, genie-1489, genie-1240, spymemcached-150, genie-1518";

        List<String> compositeIDs = CompositeUtils.convertTextToList(compositeIds);

        for (String compositeID : compositeIDs) {
            CompositeRefactoring composite = collector.compositeRepository.getCompositeById(compositeID);
            System.out.println("CompositeId:" + compositeID);
            List<String> refactorings = new ArrayList<>();
            for (String refactoringID : composite.getRefactoringIDs()) {
                Refactoring refactoring = collector.refactoringRepository.getRefactoringById(refactoringID);

                refactorings.add(refactoring.getRefactoringType());
            }

            Collections.sort(refactorings);
            System.out.println("Refactorings: " + refactorings);
        }
    }



    private List<CompositeRefactoring> getAllComposites(){
        List<CompositeRefactoring> compositeList = compositeRepository.getAllComposites();

        for (CompositeRefactoring composite: compositeList) {
            System.out.println(composite.getRefactoringIDs().size());

        }
        return compositeList;
    }



}

