package constantinexue.restseed.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import constantinexue.restseed.common.PagedList;
import constantinexue.restseed.entity.MessageEntity;
import constantinexue.restseed.mapper.MapperFactory;
import constantinexue.restseed.object.MessageObject;
import constantinexue.restseed.object.PagedObject;
import constantinexue.restseed.repository.MessageRepository;

@Singleton
@Path("/messages")
public class MessageResource extends AbstractResource {
    
    @Inject
    private MessageRepository messageRepository;
    
    @GET
    public PagedObject<MessageObject> fetchAll(@QueryParam("skip") Integer skip,
                                               @QueryParam("take") Integer take) {
        PagedList<MessageEntity> messages = messageRepository.fetchAll(skip, take);
        
        return MapperFactory.map(messages);
    }
    
    @POST
    public MessageObject create(@FormParam("author") String authorId,
                                @FormParam("text") String messageText) {
        MessageEntity message = messageRepository.create(authorId, messageText);
        
        return MapperFactory.map(message);
    }
    
    @DELETE
    @Path("/{id}")
    public String delete(@PathParam("id") String messageId) {
        messageRepository.delete(messageId);
        
        return messageId;
    }
}
