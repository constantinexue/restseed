package constantinexue.restseed.client.util;

import com.sun.jersey.api.representation.Form;

public class FormBuilder {
    
    private Form params;
    
    public FormBuilder() {
        params = new Form();
    }
    
    public FormBuilder add(String name, String value) {
        params.add(name, value);
        return this;
    }
    
    public FormBuilder add(String name, int value) {
        params.add(name, value);
        return this;
    }
    
    public FormBuilder page(int skip, int take) {
        add("skip", skip).add("take", take);
        return this;
    }
    
    public Form create() {
        return params;
    }
}
