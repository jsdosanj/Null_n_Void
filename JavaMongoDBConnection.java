package net.codejava.mongodb;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class JavaMongoDBConnection {
	public static void main(String[] args) {
		try {
			MongoClient mongoClient = new MongoClient("localhost");
			
			List<String> databases = mongoClient.getDatabaseNames();
			
			for (String dbName : databases) {
				System.out.println("- Database: " + dbName);
				
				DB db = mongoClient.getDB(dbName);
		
				Set<String> collections = db.getCollectionNames();
				for (String colName : collections) {
					System.out.println("\t + Collection: " + colName);
				}
			}	
			mongoClient.close();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
	}
	//The code below will allow us to connect our java code to mongodb
	final StitchAppClient client =
        Stitch.initializeDefaultAppClient("");

final RemoteMongoClient mongoClient =
        client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

final RemoteMongoCollection<Document> coll =
        mongoClient.getDatabase("<DATABASE>").getCollection("<COLLECTION>");

client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
        new Continuation<StitchUser, Task<RemoteUpdateResult>>() {

          @Override
          public Task<RemoteUpdateResult> then(@NonNull Task<StitchUser> task) throws Exception {
            if (!task.isSuccessful()) {
              Log.e("STITCH", "Login failed!");
              throw task.getException();
            }

            final Document updateDoc = new Document(
                    "owner_id",
                    task.getResult().getId()
            );

            updateDoc.put("number", 42);
            return coll.updateOne(
                    null, updateDoc, new RemoteUpdateOptions().upsert(true)
            );
          }
        }
).continueWithTask(new Continuation<RemoteUpdateResult, Task<List<Document>>>() {
  @Override
  public Task<List<Document>> then(@NonNull Task<RemoteUpdateResult> task) throws Exception {
    if (!task.isSuccessful()) {
      Log.e("STITCH", "Update failed!");
      throw task.getException();
    }
    List<Document> docs = new ArrayList<>();
    return coll
            .find(new Document("owner_id", client.getAuth().getUser().getId()))
            .limit(100)
            .into(docs);
  }
}).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
  @Override
  public void onComplete(@NonNull Task<List<Document>> task) {
    if (task.isSuccessful()) {
      Log.d("STITCH", "Found docs: " + task.getResult().toString());
      return;
    }
    Log.e("STITCH", "Error: " + task.getException().toString());
    task.getException().printStackTrace();
  }
});
}
