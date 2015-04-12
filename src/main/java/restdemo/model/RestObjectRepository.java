package restdemo.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import restdemo.objects.RestObject;

@RepositoryRestResource(collectionResourceRel = "api", path = "api")
public interface RestObjectRepository extends MongoRepository<RestObject, String> {

}