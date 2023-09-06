package app.piper.piper.operation.bulk;

import app.piper.piper.web.ErrorCode;
import app.piper.piper.web.PiperException;
import java.util.List;
import org.springframework.http.HttpStatus;

public class BulkOperationNotSupportedException extends PiperException {

    private static final String ERROR_MESSAGE = "Operation not supported, please check your request";

    public BulkOperationNotSupportedException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.CLIENT_ERROR, List.of(ERROR_MESSAGE), ERROR_MESSAGE);
    }

}
