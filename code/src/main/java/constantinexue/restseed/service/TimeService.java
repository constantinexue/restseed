package constantinexue.restseed.service;

import org.joda.time.LocalDateTime;

import com.google.inject.Singleton;

@Singleton
public class TimeService {
    
    public LocalDateTime getNowDateTime() {
        return LocalDateTime.now();
    }
}
