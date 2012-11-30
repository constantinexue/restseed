package constantinexue.restseed.model;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.repository.UserRepository;

public class AllUsersModel implements Iterable<UserModel> {
    
    private List<UserModel> userModels;
    
    public AllUsersModel() {
        userModels = Lists.newArrayList();
    }
    
    public void load(UserRepository userRepository) {
        userModels.clear();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            userModels.add(new UserModel(userEntity));
        }
    }
    
    @Override
    public Iterator<UserModel> iterator() {
        return userModels.iterator();
    }
    
}
