package constantinexue.guicelike.test;

import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

import constantinexue.guicelike.model.UserModel;
import constantinexue.guicelike.object.UserObject;

public class MapperTest {
    
    @Test
    public void mapUser() {
        UserModel userModel = new UserModel();
        userModel.setName("tester");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                   .enableFieldMatching(true)
                   .setFieldAccessLevel(AccessLevel.PRIVATE);
        
        UserObject userObject = modelMapper.map(userModel, UserObject.class);
        
        Assert.assertEquals(userModel.getId(), userObject.id);
        Assert.assertEquals(userModel.getName(), userObject.name);
    }
}
