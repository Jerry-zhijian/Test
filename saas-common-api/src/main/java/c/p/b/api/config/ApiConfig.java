package c.p.b.api.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * Api configuration.
 * <p>
 * Created: 2018-08-10 10:12:46
 *
 * @author Michael.Zhang
 */
public class ApiConfig {

    @Value("baymax.api.app-id-required")
    private boolean appIdRequired;

    @Value("baymax.api.request-auto-validate")
    private boolean requestAutoValidate;

    public boolean isAppIdRequired() {
        return appIdRequired;
    }

    public void setAppIdRequired(boolean appIdRequired) {
        this.appIdRequired = appIdRequired;
    }

    public boolean isRequestAutoValidate() {
        return requestAutoValidate;
    }

    public void setRequestAutoValidate(boolean requestAutoValidate) {
        this.requestAutoValidate = requestAutoValidate;
    }

}
