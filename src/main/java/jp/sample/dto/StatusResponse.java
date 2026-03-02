package jp.sample.dto;

/**
 * ステータスレスポンス
 */
public class StatusResponse {
    private int status;
    
    public StatusResponse() {
    }
    
    public StatusResponse(int status) {
        this.status = status;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}

// Made with Bob
