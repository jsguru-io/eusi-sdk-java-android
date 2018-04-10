package io.jsguru.eusisdk;

/**
 * Provides options for advanced filtering of content on Delivery API
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiQuery {

    private String queryString;

    private EusiQuery(Builder builder){
        this.queryString = builder.queryString;
    }

    public String getQueryString(){
        return queryString;
    }


    /**
     * Builder class for EusiQuery
     *
     * @see EusiQuery
     */
    public static class Builder {
        String queryString = "?";

        /**
         * @param contentId Content Id
         * @return Builder
         */
        public Builder withId(String contentId) {
            prepareString();
            queryString += "sys.key=" + contentId;
            return this;
        }

        /**
         * @param contentName Content name
         * @return Builder
         */
        public Builder withName(String contentName) {
            prepareString();
            queryString += "sys.name=" + contentName;
            return this;
        }

        /**
         * @param contentType Content type
         * @return Builder
         */
        public Builder withType(String contentType) {
            prepareString();
            queryString += "sys.type=" + contentType;
            return this;
        }

        /**
         * @param contentTaxonomy Root taxonomy TODO is this only with root taxonomy or ?
         * @return Builder
         */
        public Builder withTaxonomy(String contentTaxonomy) {
            prepareString();
            queryString += "sys.taxonomy=" + contentTaxonomy;
            return this;
        }

        /**
         * @param taxonomyPath Taxonomy path
         * @return Builder
         */
        public Builder withTaxonomyPath(String taxonomyPath) {
            prepareString();
            queryString += "sys.taxonomy.path=" + taxonomyPath;
            return this;
        }

        /**
         * @param elementName Element name
         * @param elementValue Element value
         * @return Builder
         */
        public Builder withElement(String elementName, String elementValue) {
            prepareString();
            queryString += "elem" + elementName + "=" + elementValue;
            return this;
        }

        /**
         * @param query Raw filter query
         * @return Builder
         */
        public Builder withRawQuery(String query) {
            if(query.startsWith("?"))
                queryString = "";
            else if(!query.startsWith("&"))
                prepareString();
            queryString += query;
            return this;
        }


        // Helper
        private void prepareString(){
            if("".equals(queryString))
                queryString = "?";
            else if("?".equals(queryString))
                queryString+="";
            else
                queryString+="&";
        }


        /**
         * @return EusiQuery from Builder.this
         */
        public EusiQuery build(){
            return new EusiQuery(this);
        }
    }

}
