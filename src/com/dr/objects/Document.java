package com.dr.objects;

/**
 * Created by Pranit on 11/18/13.
 */
public class Document {
    int id;
    
    public void setId(int id) {
	this.id = id;
    }
    
    public int getId() {
	return this.id;
    }

    @Override
    public String toString()
    {
	return "Document [id=" + id + "]";
    }
    
    
}
