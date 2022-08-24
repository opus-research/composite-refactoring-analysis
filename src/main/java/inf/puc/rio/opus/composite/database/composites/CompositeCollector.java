package inf.puc.rio.opus.composite.database.composites;

import inf.puc.rio.opus.composite.analysis.utils.CompositeUtils;
import inf.puc.rio.opus.composite.database.refactorings.RefactoringRepository;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;
import inf.puc.rio.opus.composite.model.effect.CompositeDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeCollector {

    private CompositeRepository compositeRepository;
    private RefactoringRepository refactoringRepository;

    public CompositeCollector(String[] args){
        compositeRepository= new CompositeRepository(args);
        refactoringRepository= new RefactoringRepository(args);
    }

    public static void main(String[] args) {
        CompositeCollector collector = new CompositeCollector(args);

        // List<CompositeRefactoring> composites = collector.getAllComposites();




    }

    public List<CompositeDTO> getCompositesByIds(String compositeIDs){

        List<String> compositeIDList = CompositeUtils.convertTextToList(compositeIDs);
        List<CompositeDTO> composites = new ArrayList<>();

        for (String compositeID : compositeIDList) {
            CompositeRefactoring composite = compositeRepository.getCompositeById(compositeID);

            List<String> refactorings = new ArrayList<>();
            for (String refactoringID : composite.getRefactoringIDs()) {
                Refactoring refactoring = refactoringRepository.getRefactoringById(refactoringID);
                refactorings.add(refactoring.getRefactoringType());
            }

            CompositeDTO compositeDTO = CompositeUtils.setCompositeDTO(composite);
            Collections.sort(refactorings);
            compositeDTO.setRefactoringsAsTextList(refactorings);
            composites.add(compositeDTO);
        }

        return composites;

    }


    public List<CompositeDTO> getCompositesByIdsWithRefsObj(String compositeIDs){

        List<String> compositeIDList = CompositeUtils.convertTextToList(compositeIDs);
        List<CompositeDTO> composites = new ArrayList<>();

        for (String compositeID : compositeIDList) {

            CompositeRefactoring composite = compositeRepository.getCompositeById(compositeID);

            List<Refactoring> refactorings = new ArrayList<>();

            for (String refactoringID : composite.getRefactoringIDs()) {
                Refactoring refactoring = refactoringRepository.getRefactoringById(refactoringID);
                refactorings.add(refactoring);
            }

            CompositeDTO compositeDTO = CompositeUtils.setCompositeDTO(composite);
            compositeDTO.setRefactoringsList(refactorings);
            composites.add(compositeDTO);
        }

        return composites;

    }

    private List<CompositeRefactoring> getAllComposites(){
        List<CompositeRefactoring> compositeList = compositeRepository.getAllComposites();

        for (CompositeRefactoring composite: compositeList) {
            System.out.println(composite.getRefactoringIDs().size());

        }
        return compositeList;
    }



}

