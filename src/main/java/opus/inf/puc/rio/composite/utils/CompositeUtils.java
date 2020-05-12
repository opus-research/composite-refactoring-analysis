package opus.inf.puc.rio.composite.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeUtils {

	public static List<String> convertRefactoringsTextToRefactoringsList(String refactoringsText){
		
		List<String> refactorings = new ArrayList<String>();
		
		refactoringsText = refactoringsText.replace("[", "");
		refactoringsText = refactoringsText.replace("]", "");
		
		refactorings = Arrays.asList(refactoringsText.split("\\s*,\\s*"));
		
		return refactorings;
		
	}
}
