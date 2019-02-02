package br.com.loginphp;

public class Address {
    private String reference;
    private String url;

    public Address(String reference, String url){
        this.reference = reference;
        this.url = url;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
