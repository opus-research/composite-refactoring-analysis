package inf.puc.rio.opus.composite.database.composites;


import com.mongodb.client.FindIterable;
import inf.puc.rio.opus.composite.database.Repository;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class CompositeRepository extends Repository {

	//https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
	// https://www.baeldung.com/java-mongodb
	//https://mongodb.github.io/mongo-java-driver/3.5/driver/getting-started/quick-start-pojo/

	public CompositeRepository(String[] args){
		init(args);
	}

	public List<CompositeRefactoring> getAllComposites() {

		List<CompositeRefactoring> composites = composites().find().into(new ArrayList<CompositeRefactoring>());

		for (CompositeRefactoring composite: composites) {
			System.out.println(composite);
		}

		return composites;
	}

}
