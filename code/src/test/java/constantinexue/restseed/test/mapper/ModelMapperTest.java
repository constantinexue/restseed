package constantinexue.restseed.test.mapper;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import constantinexue.restseed.mapper.MapperFactory;
import constantinexue.restseed.model.UserModel;
import constantinexue.restseed.object.UserObject;
import constantinexue.restseed.test.AbstractTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelMapperTest extends AbstractTest {
    
    @Test
    public void map() {
        ModelMapper mapper = MapperFactory.createModelMapper();
        
        UserModel model = mock(UserModel.class);
        when(model.getId()).thenReturn("1234");
        UserObject object = mapper.map(model, UserObject.class);
        
        assertEquals(model.getId(), object.id);
    }
}
