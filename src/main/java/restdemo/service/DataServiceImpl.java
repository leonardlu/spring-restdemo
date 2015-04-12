package restdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restdemo.model.RestObjectRepository;
import restdemo.objects.RestObject;

/**
 * 
 * @author leonardlu
 *
 */

@Service
public class DataServiceImpl implements IDataService {

	private final RestObjectRepository repo;
	
	@Autowired
	public DataServiceImpl(RestObjectRepository repo){
		this.repo = repo;
	}
	
	@Override
	public List<RestObject> getAllObjects() {
		return repo.findAll();
	}

	@Override
	public RestObject create(RestObject obj) {
		return repo.save(obj);
	}

	@Override
	public void delete(String id) {
		repo.delete(id);
	}

	@Override
	public RestObject update(String id, String str) {
		RestObject object = findById(id);
		object.setContents(str);
		return repo.save(object);
	}

	@Override
	public RestObject findById(String id) {
		return repo.findOne(id);
	}

}
