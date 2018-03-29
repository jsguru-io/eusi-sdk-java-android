package io.jsguru.eusisdk.models.taxonomy;

import java.util.ArrayList;

/**
 * Created by Petar Suvajac on 3/26/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiTaxonomy {
    private String id;
    private String name;
    private String taxonomyId;
    private String parentId;
    private String path;
    private int hierarchyLevel;
    private ArrayList<EusiTaxonomy> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(String taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(int hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public ArrayList<EusiTaxonomy> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<EusiTaxonomy> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        EusiTaxonomy taxonomy;
        try {
            taxonomy = (EusiTaxonomy) obj;
        } catch (Exception e) {
            return false;
        }

        if (taxonomy.getId().equals(this.getId()))
            return true;

        return false;
    }
}
