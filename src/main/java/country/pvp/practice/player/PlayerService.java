package country.pvp.practice.player;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import country.pvp.practice.data.mongo.MongoRepository;
import org.jetbrains.annotations.NotNull;

public class PlayerService extends MongoRepository<PracticePlayer> {

    @Inject
    public PlayerService(MongoDatabase database) {
        super(database);
    }

    @Override
    public void load(@NotNull PracticePlayer entity) {
        org.bson.Document document = database.getCollection(entity.getCollection()).find(Filters.eq("_id", entity.getId())).first();

        if (document == null) {
            save(entity);
            return;
        }

        entity.applyDocument(document);
    }
}
