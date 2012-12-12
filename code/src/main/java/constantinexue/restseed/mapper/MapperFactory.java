package constantinexue.restseed.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import constantinexue.restseed.model.UserModel;
import constantinexue.restseed.object.UserObject;

public abstract class MapperFactory {
    
    public static ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        Configuration config = modelMapper.getConfiguration();
//        config.setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
//        config.setDestinationNameTransformer(NameTransformers.JAVABEANS_MUTATOR);
        modelMapper.addMappings(new UserMap());
        // modelMapper.addConverter(new UserConverter());
        
        return modelMapper;
    }
    
    static class UserMap extends PropertyMap<UserModel, UserObject> {
        
        @Override
        protected void configure() {
            map(new UserObject()).id = source.getId();
        }
        
    }
    
    static class UserConverter extends AbstractConverter<UserModel, UserObject> {
        
        @Override
        protected UserObject convert(UserModel source) {
            UserObject object = new UserObject();
            object.id = source.getId();
            
            return object;
        }
        
    }
    
    static class UserProvider extends AbstractProvider<UserObject> {
        
        @Override
        protected UserObject get() {
            return new UserObject();
        }
    }
}
