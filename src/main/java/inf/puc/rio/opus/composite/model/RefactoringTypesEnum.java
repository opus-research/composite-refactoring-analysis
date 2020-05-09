package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RefactoringTypesEnum {

	
	
	
	EXTRACT_METHOD{
		 public String toString() {
	          return "Extract Method";
	      }
	}, 
	EXTRACT_CLASS{
		 public String toString() {
	          return "Extract Class";
	      }
	},
	EXTRACT_SUPERCLASS{
		 public String toString() {
	          return "Extract Superclass";
	      }
	}, 
	MOVE_METHOD{
		 public String toString() {
	          return "Move Method";
	      }
	},
	MOVE_CLASS{
		 public String toString() {
	          return "Move Class";
	      }
	}, 
	INLINE_METHOD{
		 public String toString() {
	          return "Inline Method";
	      }
	},
	PULL_UP{
		 public String toString() {
			  return "[Pull Up Attribute, Pull Up Method]";
	     }
	},
	PUSH_DOWN{
		 public String toString() {
			 return "[Push Down Attribute, Push Down Method]";
	     }
	},
	RENAME{
		 public String toString() {	  
	          return "[Rename Variable, Rename Attribute, Rename Method, Rename Class]";
	     }
	},
	REFACT_VARIABLE{
		
		 public String toString() {
	          return "[Extract Variable, Inline Variable, Parameterize Variable,"
	          		+ "Rename Variable, Rename Parameter, Rename Attribute, Replace Variable with Attribute,"
	          		+ "Replace Attribute, Merge Variable, Merge Parameter, Merge Attribute, Split Variable, Split Parameter,"
	          		+ "Split Attribute, Change Variable Type, Change Parameter Type, Change Return Type, Change Attribute Type, Extract Attribute]";
	      }
	},
	
	

}
