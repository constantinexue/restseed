package constantinexue.restseed.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.PersistService;

import constantinexue.restseed.entity.UserEntity;
import constantinexue.restseed.util.PropertiesNames;
import constantinexue.restseed.util.SQLHelper;

@Singleton
public class RepositoryInitializer {
    
    @Named(PropertiesNames.CONNECTION_URL)
    @Inject
    private String connectionUrl;
    
    @Inject
    private UserRepository userRepository;
    @Inject
    private MessageRepository messageRepository;
    
    @Inject
    public RepositoryInitializer(PersistService service) {
        service.start();
    }
    
    @Inject
    public void initialize() throws Exception {
        // 初始化数据库，建表
        SQLHelper.executeScript(connectionUrl, "./conf/create.sql");
        
        // 创建一些测试用户
        for (int i = 0; i < 10; i++) {
            UserEntity user = userRepository.create("u" + i, "123");
            for (int j = 0; j < 10; j++) {
                messageRepository.create(user.getId(), user.getUsername() + " said " + "中文");
            }
        }
    }
}
