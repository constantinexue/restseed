package constantinexue.guicelike.resource;

import javax.ws.rs.Path;

import com.google.inject.Singleton;

@Singleton
@Path("/messages")
public class MessageResource extends AbstractResource {
}
