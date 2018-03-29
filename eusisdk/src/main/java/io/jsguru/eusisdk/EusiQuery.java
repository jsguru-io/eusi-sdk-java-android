package io.jsguru.eusisdk;

/**
 * Created by Petar Suvajac on 3/13/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiQuery {

    private String queryString;

    private EusiQuery(Builder builder){
        //TODO validate quesryString ??
        this.queryString = builder.queryString;
    }

    public String getQueryString(){
        return queryString;
    }


    /**
     * Builder.
     */
    public static class Builder {
        String queryString = "?";

        public Builder withName(String contentName) {
            prepareString();
            queryString += "sys.name=" + contentName;
            return this;
        }


        public Builder withType(String contentType) {
            prepareString();
            queryString += "sys.type=" + contentType;
            return this;
        }

        public Builder withTaxonomy(String contentTaxonomy) {
            prepareString();
            queryString += "sys.taxonomy=" + contentTaxonomy;
            return this;
        }

        public Builder withTaxonomyPath(String taxonomyPath) {
            prepareString();
            queryString += "sys.taxonomy.path=" + taxonomyPath;
            return this;
        }

        public Builder withElement(String elementName, String elementValue) {
            prepareString();
            queryString += "elem" + elementName + "=" + elementValue;
            return this;
        }

        public Builder withRawQuery(String query) {
            if(query.startsWith("?"))
                queryString = "";
            else if(!query.startsWith("&"))
                prepareString();
            queryString += query;
            return this;
        }


        private void prepareString(){
            if("".equals(queryString))
                queryString = "?";
            else if("?".equals(queryString))
                queryString+="";
            else
                queryString+="&";
        }

        public EusiQuery build(){
            return new EusiQuery(this);
        }


    }

}
