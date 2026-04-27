package au.com.equifax.cicddemo.domain;

public class EnvDetail {
    private String ip;
    private String hostname;
    private String os;
    private String version;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; 
}

    
}