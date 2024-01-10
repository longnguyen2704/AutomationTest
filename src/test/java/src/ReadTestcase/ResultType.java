package src.ReadTestcase;

public class ResultType {
    private String selector;
    private String action;
    private String status;

    // Constructors
    public ResultType() {
    }

    public ResultType(String selector, String action, String status) {
        this.selector = selector;
        this.action = action;
        this.status = status;
    }

    // Getters and Setters
    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
