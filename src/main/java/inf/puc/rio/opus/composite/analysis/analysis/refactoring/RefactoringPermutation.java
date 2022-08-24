package inf.puc.rio.opus.composite.analysis.analysis.refactoring;

import java.util.ArrayList;
import java.util.List;

import inf.puc.rio.opus.composite.model.refactoring.Refactoring;



public class RefactoringPermutation {

	  
	  private List<ArrayList<Refactoring>> refactoringSequences;
	
	  public RefactoringPermutation(){
		  this.refactoringSequences = new ArrayList<ArrayList<Refactoring>>();
	  }
	  
	  
	  public List<ArrayList<Refactoring>> getRefactoringSequences() {
		return refactoringSequences;
	 }

	public List<ArrayList<Refactoring>> performPermutations(List<Refactoring> refacts){

		List<ArrayList<Refactoring>> result = new ArrayList<ArrayList<Refactoring>>();
		if (refactoringSequences.size() <= 200) {

			if (refacts.size() <= 7 && refacts != null && refacts.size() > 1) {
				refactoringSequences.add(new ArrayList<>(refacts));

			}

			if (refacts == null) {
				return result;
			}

			for (int i = 0; i < refacts.size(); i++) {

				List<Refactoring> rems = new ArrayList<Refactoring>();
				rems.addAll(refacts.subList(0, i));
				rems.addAll(refacts.subList(i + 1, refacts.size()));

				List<ArrayList<Refactoring>> remaining = performPermutations(rems);

				for (int j = 0; j < remaining.size(); j++) {
					remaining.get(j).add(refacts.get(i));
					result.addAll(remaining);
				}
			}

		}
		return result;
	}

	public List<ArrayList<Refactoring>> getRefactoringSequencePermutations() {
		// TODO Auto-generated method stub
		return refactoringSequences;
	}
}
