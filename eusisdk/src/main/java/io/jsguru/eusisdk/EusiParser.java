package io.jsguru.eusisdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.jsguru.eusisdk.models.EusiPagination;
import io.jsguru.eusisdk.models.content.EusiContent;
import io.jsguru.eusisdk.models.content.EusiContentCodePicker;
import io.jsguru.eusisdk.models.content.EusiContentDatePicker;
import io.jsguru.eusisdk.models.content.EusiContentDocumentPicker;
import io.jsguru.eusisdk.models.content.EusiContentImagePicker;
import io.jsguru.eusisdk.models.content.EusiContentLocationPicker;
import io.jsguru.eusisdk.models.content.EusiContentMediaPicker;
import io.jsguru.eusisdk.models.content.EusiContentNumberPicker;
import io.jsguru.eusisdk.models.content.EusiContentResponse;
import io.jsguru.eusisdk.models.content.EusiContentRichTextPicker;
import io.jsguru.eusisdk.models.content.EusiContentTaxonomyPicker;
import io.jsguru.eusisdk.models.content.EusiContentTextPicker;
import io.jsguru.eusisdk.models.content.EusiContentType;
import io.jsguru.eusisdk.models.content.helpers.Document;
import io.jsguru.eusisdk.models.content.helpers.Image;
import io.jsguru.eusisdk.models.content.helpers.Media;
import io.jsguru.eusisdk.models.content.helpers.TaxonomyItem;
import io.jsguru.eusisdk.models.form.EusiFormField;
import io.jsguru.eusisdk.models.form.EusiFormResponse;
import io.jsguru.eusisdk.models.taxonomy.EusiTaxonomy;
import io.jsguru.eusisdk.models.taxonomy.EusiTaxonomyResponse;

/**
 * Created by Petar Suvajac on 3/22/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

class EusiParser {

    EusiContentResponse parseContent(String response){
        EusiContentResponse eusiContentResponse = new EusiContentResponse();
        eusiContentResponse.setResponseString(response);
        try {
            JSONObject responseObject = new JSONObject(response);

            //pagination
            JSONObject paginationObject = responseObject.getJSONObject("pagination");
            EusiPagination pagination = new EusiPagination();
            pagination.setCount(paginationObject.getInt("count"));
            pagination.setPage(paginationObject.getInt("page"));
            pagination.setTotal(paginationObject.getInt("total"));
            pagination.setTotalPages(paginationObject.getInt("total_pages"));
            eusiContentResponse.setPagination(pagination);

            //content
            JSONArray contentArray = responseObject.getJSONArray("data");
            ArrayList<EusiContent> contentList = new ArrayList<>();
            for(int i=0; i<contentArray.length(); i++){
                JSONObject contentItemObject = contentArray.getJSONObject(i);
                EusiContent contentItem = new EusiContent();
                contentItem.setName(contentItemObject.getString("name"));
                contentItem.setId(contentItemObject.getString("id"));
                contentItem.setKey(contentItemObject.getString("key"));

                JSONArray arr = contentItemObject.getJSONArray("content");
                ArrayList<EusiContentType> list = new ArrayList<>();
                EusiContentType oneContent = null;
                for(int j=0; j < arr.length(); j++){
                    oneContent = parseOneContentType(arr.getJSONObject(j));
                    if(oneContent != null)
                        list.add(oneContent);
                }

                contentItem.setContent(list);
                contentList.add(contentItem);
            }

            eusiContentResponse.setContentList(contentList);
        } catch (JSONException e) {
            eusiContentResponse = null;
            e.printStackTrace();
        }

        return eusiContentResponse;
    }

    EusiTaxonomyResponse parseTaxonomy(String response){
        EusiTaxonomyResponse eusiTaxonomyResponse = new EusiTaxonomyResponse();
        eusiTaxonomyResponse.setResponseString(response);

        try {
            JSONObject object = new JSONObject(response);
            eusiTaxonomyResponse.setId(object.getString("id"));
            eusiTaxonomyResponse.setKey(object.getString("key"));
            eusiTaxonomyResponse.setName(object.getString("name"));
            eusiTaxonomyResponse.setBucketId(object.getString("bucket_id"));

            ArrayList<EusiTaxonomy> taxonomyList = new ArrayList<>();
            JSONObject items = object.getJSONObject("items");
            JSONArray array = items.getJSONArray("rows");
            for(int i=0; i < array.length(); i++){
                EusiTaxonomy taxonomy = parseTaxonomy(taxonomyList, array.getJSONObject(i));
                if(taxonomy != null && !taxonomyList.contains(taxonomy))
                    taxonomyList.add(taxonomy);
            }

            eusiTaxonomyResponse.setTaxonomyItems(taxonomyList);
        } catch (JSONException e) {
            eusiTaxonomyResponse = null;
            e.printStackTrace();
        }

        return eusiTaxonomyResponse;
    }

    EusiFormResponse parseForm(String response){
        EusiFormResponse formResponse = new EusiFormResponse();
        formResponse.setResponseString(response);

        try {
            JSONObject object = new JSONObject(response);
            formResponse.setName(object.getString("name"));
            formResponse.setId(object.getString("id"));
            formResponse.setKey(object.getString("key"));
            formResponse.setRedirectUrl(object.getString("redirect_url"));
            formResponse.setBucketId(object.getString("bucket_id"));

            ArrayList<EusiFormField> fields = new ArrayList<>();
            JSONArray array = object.getJSONArray("fields");
            for(int i=0; i < array.length(); i++){
                EusiFormField field = new EusiFormField();
                JSONObject item = array.getJSONObject(i);
                field.setKey(item.getString("key"));
                field.setType(item.getString("type"));
                field.setRequired(item.getBoolean("required"));

                if(item.has("allowed_types")){
                    JSONArray arr = item.getJSONArray("allowed_types");
                    ArrayList<String> allowed = new ArrayList<>();
                    for (int j=0; j < arr.length(); j++){
                        allowed.add(j, arr.getString(j));
                    }
                    field.setAllowedValues(allowed);
                }

                fields.add(i, field);
            }

            formResponse.setFields(fields);
        } catch (JSONException e) {
            formResponse = null;
            e.printStackTrace();
        }

        return formResponse;
    }

    boolean parseSubmitForm(String response){
        boolean successful = false;
        try {
            JSONObject object = new JSONObject(response);
            if(object.has("success"))
                successful = object.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return successful;
    }


    //Helpers
    private EusiContentType parseOneContentType(JSONObject content) throws JSONException{
        String type = content.getString("object_type");

        if(type == null || type.isEmpty()){
            return null;
        } else if (type.equals("input-text")){
            EusiContentTextPicker picker = new EusiContentTextPicker();
            picker.setName(content.getString("type"));
            picker.setText(content.getString("value"));
            return picker;
        } else if(type.equals("input-number")){
            EusiContentNumberPicker picker = new EusiContentNumberPicker();
            picker.setName(content.getString("type"));
            picker.setNumber(content.getInt("value"));
            return picker;
        } else if(type.equals("rich-text-input")){
            EusiContentRichTextPicker picker = new EusiContentRichTextPicker();
            picker.setName(content.getString("type"));
            picker.setText(content.getString("value"));
            return picker;
        } else if(type.equals("code-picker")){
            EusiContentCodePicker picker = new EusiContentCodePicker();
            picker.setName(content.getString("type"));
            JSONObject value = content.getJSONObject("value");
            picker.setText(value.getString("text"));
            picker.setLanguage(value.getString("language"));
            return picker;
        } else if(type.equals("date-picker")){
            EusiContentDatePicker picker = new EusiContentDatePicker();
            picker.setName(content.getString("type"));
            String timeString = content.getString("value");
            picker.setTimeString(timeString);

            //parse time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Date date = sdf.parse(timeString);
                picker.setTime(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return picker;
        } else if(type.equals("location-picker")){
            EusiContentLocationPicker picker = new EusiContentLocationPicker();
            picker.setName(content.getString("type"));
            JSONObject value = content.getJSONObject("value");
            picker.setAddress(value.getString("address"));
            picker.setLatitude(value.getDouble("lat"));
            picker.setLongitude(value.getDouble("lng"));
            return picker;
        } else if(type.equals("picker-taxonomy")){
            EusiContentTaxonomyPicker picker = new EusiContentTaxonomyPicker();
            picker.setPickerName(content.getString("type"));

            JSONObject taxonomy = content.getJSONObject("taxonomy");
            picker.setTaxonomyName(taxonomy.getString("name"));
            picker.setTaxonomyId(taxonomy.getString("id"));
            picker.setTaxonomyKey(taxonomy.getString("key"));

            ArrayList<String> taxonomyList = new ArrayList<>();
            ArrayList<TaxonomyItem> taxonomies = new ArrayList<>();
            JSONArray arr = taxonomy.getJSONArray("taxonomy_items");
            for(int i=0; i < arr.length(); i++){
                taxonomies.add(i, parseOneTaxonomyItem(arr.getJSONObject(i)));
                taxonomyList.add(taxonomies.get(i).getName());
            }

            picker.setTaxonomyItems(taxonomies);
            picker.setTaxonomyItemsList(taxonomyList);
            return picker;
        } else if(type.equals("document-picker")){
            EusiContentDocumentPicker picker = new EusiContentDocumentPicker();
            picker.setName(content.getString("type"));

            ArrayList<String> documentList = new ArrayList<>();
            ArrayList<Document> documents = new ArrayList<>();
            JSONArray arr = content.getJSONArray("media");
            for(int i=0; i < arr.length(); i++){
                JSONObject item = arr.getJSONObject(i);
                Document doc = new Document();
                doc.setId(item.getString("id"));
                doc.setUrl(item.getString("url"));
                documents.add(doc);
                documentList.add(doc.getId());
            }

            picker.setDocuments(documents);
            picker.setDocumentList(documentList);
            return picker;
        } else if(type.equals("image-picker")){
            EusiContentImagePicker picker = new EusiContentImagePicker();
            picker.setName(content.getString("type"));

            ArrayList<String> imageList = new ArrayList<>();
            ArrayList<Image> images = new ArrayList<>();
            JSONArray arr = content.getJSONArray("media");
            for(int i=0; i < arr.length(); i++){
                JSONObject item = arr.getJSONObject(i);
                Image image = new Image();
                image.setId(item.getString("id"));
                image.setUrl(item.getString("url"));
                image.setWidth(item.getInt("width"));
                image.setHeight(item.getInt("height"));
                images.add(image);
                imageList.add(image.getId());
            }

            picker.setImages(images);
            picker.setImageList(imageList);
            return picker;
        } else if(type.equals("media-picker")){
            EusiContentMediaPicker picker = new EusiContentMediaPicker();
            picker.setName(content.getString("type"));

            ArrayList<String> mediaList = new ArrayList<>();
            ArrayList<Media> media = new ArrayList<>();
            JSONArray arr = content.getJSONArray("media");
            for(int i=0; i < arr.length(); i++){
                JSONObject itemObject = arr.getJSONObject(i);
                Media item = new Media();
                item.setId(itemObject.getString("id"));
                item.setUrl(itemObject.getString("url"));
                item.setType(itemObject.getString("type"));
                if(item.getType().equalsIgnoreCase("image")){
                    item.setWidth(itemObject.getInt("width"));
                    item.setHeight(itemObject.getInt("height"));
                }
                media.add(item);
                mediaList.add(item.getId());
            }

            picker.setMedia(media);
            picker.setMediaList(mediaList);
            return picker;
        }
        //unknown type
        return null;
    }

    private TaxonomyItem parseOneTaxonomyItem(JSONObject itemObject) throws JSONException{
        TaxonomyItem item = new TaxonomyItem();
        item.setId(itemObject.getString("id"));
        item.setName(itemObject.getString("name"));
        item.setPath(itemObject.getString("path"));
        return item;
    }

    private EusiTaxonomy parseTaxonomy(ArrayList<EusiTaxonomy> list, JSONObject object) throws JSONException {
        EusiTaxonomy taxonomy = new EusiTaxonomy();
        taxonomy.setId(object.getString("id"));
        taxonomy.setName(object.getString("name"));
        taxonomy.setParentId(object.getString("parent_id"));
        taxonomy.setTaxonomyId(object.getString("taxonomy_id"));
        taxonomy.setPath(object.getString("path"));
        taxonomy.setHierarchyLevel(object.getInt("hierarchy_level"));

        if(object.has("children")){
            ArrayList<EusiTaxonomy> children = new ArrayList<>();
            JSONArray arr = object.getJSONArray("children");
            for(int i=0; i < arr.length(); i++){
                EusiTaxonomy temp = parseTaxonomy(list, arr.getJSONObject(i));
                if(temp != null){
                    children.add(temp);
                }
            }
            taxonomy.setChildren(children);
        }

        //add taxonomy to defaultList in TaxonomyResponse
        if(!list.contains(taxonomy))
            list.add(taxonomy);
        return taxonomy;
    }

}
