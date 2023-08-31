package app.piper.piper.setting.store;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class SettingMissingException extends HttpServerErrorException {

    public SettingMissingException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Missing settings");
    }

}
