package app.piper.piper.operation.bulk;

public enum BulkExecutionMode {

    SYNC, // Server respond back after all operation done, can catch errors

    ASYNC // Server respond immediately with acknowledgement of the request

}
