import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBApp {
    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("assignment");
        MongoCollection<Document> employee = database.getCollection("employee");
        FindIterable<Document> documents = employee.find();
    }
}
