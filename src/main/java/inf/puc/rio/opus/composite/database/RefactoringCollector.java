package inf.puc.rio.opus.composite.database.refactoring;


import java.util.List;

public class RefactoringCollector {

    private RefactoringRepository refactoringRepository;

    private RefactoringCollector(String[] args){
        refactoringRepository= new RefactoringRepository(args);
    }

    public static void main(String[] args) {
        RefactoringCollector collector = new RefactoringCollector(args);

    }


}

