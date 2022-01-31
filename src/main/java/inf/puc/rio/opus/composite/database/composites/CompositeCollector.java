package inf.puc.rio.opus.composite.database.composites;

import inf.puc.rio.opus.composite.model.CompositeRefactoring;

import java.util.List;

public class CompositeCollector {

    private CompositeRepository compositeRepository;

    private CompositeCollector(String[] args){
        compositeRepository= new CompositeRepository(args);
    }
    public static void main(String[] args) {
        CompositeCollector collector = new CompositeCollector(args);

        List<CompositeRefactoring> composites = collector.getAllComposites();

    }



    private List<CompositeRefactoring> getAllComposites(){
        List<CompositeRefactoring> compositeList = compositeRepository.getAllComposites();

        for (CompositeRefactoring composite: compositeList) {
            System.out.println(composite.getRefactoringIDs().size());

        }
        return compositeList;
    }



}

