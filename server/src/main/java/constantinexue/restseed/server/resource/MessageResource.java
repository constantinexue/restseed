package constantinexue.restseed.server.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.inject.Inject;

import constantinexue.restseed.common.object.MessageObject;
import constantinexue.restseed.common.object.PagedObject;
import constantinexue.restseed.server.common.PagedList;
import constantinexue.restseed.server.entity.MessageEntity;
import constantinexue.restseed.server.mapper.MapperFactory;
import constantinexue.restseed.server.repository.MessageRepository;

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
    public MessageObject create(@HeaderParam("author") String authorId,
                                @FormParam("text") String messageText) {
        MessageEntity message = messageRepository.create(authorId, messageText);
        
        return MapperFactory.map(message);
    }
    
    @DELETE
    @Path("/{id}")
    public String delete(@HeaderParam("author") String authorId,
                         @PathParam("id") String messageId) {
        MessageEntity message = messageRepository.fetch(messageId);
        if (message.getAuthor().getId().equals(authorId)) {
            messageRepository.delete(messageId);
        }
        
        return messageId;
    }
}
