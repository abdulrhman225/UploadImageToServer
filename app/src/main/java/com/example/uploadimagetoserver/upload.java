package com.example.uploadimagetoserver;

public class upload {
    String success ;
    String message;
    String file;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public upload(String success, String message, String file) {
        this.success = success;
        this.message = message;
        this.file = file;
    }
}
