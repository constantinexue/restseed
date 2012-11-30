package constantinexue.restseed.test;

import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

import constantinexue.restseed.model.UserModel;
import constantinexue.restseed.object.UserObject;

public class MapperTest extends Assert {
    
    @Test
    public void mapUser() {
        UserModel userModel = new UserModel();
        userModel.setName("tester");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                   .enableFieldMatching(true)
                   .setFieldAccessLevel(AccessLevel.PRIVATE);
        
        UserObject userObject = modelMapper.map(userModel, UserObject.class);
        
        assertEquals(userModel.getId(), userObject.id);
        assertEquals(userModel.getName(), userObject.name);
    }
}
