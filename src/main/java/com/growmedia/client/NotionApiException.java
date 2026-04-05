package com.growmedia.client;

/**
 * Notion API 통신 실패 시 던지는 커스텀 예외.
 */
public class NotionApiException extends RuntimeException{
    public NotionApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
