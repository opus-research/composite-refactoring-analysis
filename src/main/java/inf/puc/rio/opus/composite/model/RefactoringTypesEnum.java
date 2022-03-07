package inf.puc.rio.opus.composite.model;

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
			  return "Pull Up Attribute, Pull Up Method";
	     }
	},
	PUSH_DOWN{
		 public String toString() {
			 return "Push Down Attribute, Push Down Method";
	     }
	},
	RENAME{
		 public String toString() {	  
	          return "Rename Parameter, Rename Variable, Rename Attribute, Rename Method, Rename Class";
	     }
	},
	EXTRACT_VARIABLE{
		public String toString() {
			return "Extract Variable";
		}
	},
	INLINE_VARIABLE{
		public String toString() {
			return "Inline Variable";
		}
	},
	PARAMETERIZE_VARIABLE{
		public String toString() {
			return "Parameterize Variable";
		}
	},
	REPLACE_VARIABLE_WITH_ATTRIBUTE{
		public String toString() {
			return "Replace Variable with Attribute";
		}
	},
	REPLACE_ATTRIBUTE{
		public String toString() {
			return "Replace Attribute";
		}
	},
	MERGE_VARIABLE{
		public String toString() {
			return "Merge Variable";
		}
	},
	MERGE_PARAMETER{
		public String toString() {
			return "Merge Parameter";
		}
	},
	MERGE_ATTRIBUTE{
		public String toString() {
			return "Merge Attribute";
		}
	},
	SPLIT_VARIABLE{
		public String toString() {
			return "Split Variable";
		}
	},
	SPLIT_PARAMETER{
		public String toString() {
			return "Split Parameter";
		}
	},
	SPLIT_ATTRIBUTE{
		public String toString() {
			return "Split Attribute";
		}
	},
	CHANGE_VARIABLE_TYPE{
		public String toString() {
			return "Change Variable Type";
		}
	},
	CHANGE_PARAMETER_TYPE{
		public String toString() {
			return "Change Parameter Type";
		}
	},
	CHANGE_RETURN_TYPE{
		public String toString() {
			return "Change Return Type";
		}
	},
	CHANGE_ATTRIBUTE_TYPE{
		public String toString() {
			return "Change Attribute Type";
		}
	},
	EXTRACT_ATTRIBUTE{
		public String toString() {
			return "Extract Attribute";
		}
	}
	


}
