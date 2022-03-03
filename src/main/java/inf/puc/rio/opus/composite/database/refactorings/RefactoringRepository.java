package inf.puc.rio.opus.composite.database.refactorings;


import com.mongodb.BasicDBObject;
import inf.puc.rio.opus.composite.database.Repository;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.Refactoring;

import java.util.ArrayList;
import java.util.List;


public class RefactoringRepository extends Repository {

	//https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
	// https://www.baeldung.com/java-mongodb
	//https://mongodb.github.io/mongo-java-driver/3.5/driver/getting-started/quick-start-pojo/

	public RefactoringRepository(String[] args){
		init(args);
	}


	public Refactoring getRefactoringById(String id) {

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("refactoringId", id.trim());
		Refactoring refactoring = refactorings().find(searchQuery).first();


		return refactoring;
	}

}
