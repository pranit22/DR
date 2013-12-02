package com.dr.objects.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dr.helpers.DatabaseManager;
import com.dr.objects.Document;
import com.dr.objects.JobApplication;

import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    private static final String TABLE_DOCUMENT = "document";

    // columns
    private static final String ID = "_id";
    private static final String TITLE = "title";
    private static final String FILENAME = "filename";
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "type";
    private static String[] allColumns = {ID, TITLE, FILENAME, DESCRIPTION, TYPE};

    // create table
    public static final String CREATE_TABLE = "" + "CREATE TABLE " + TABLE_DOCUMENT
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT, "
            + FILENAME + " TEXT, "
            + DESCRIPTION + " TEXT, "
            + TYPE + " INTEGER "
            + ")";

    // Database fields
    private SQLiteDatabase database;
    private DatabaseManager dbHelper;

    public DocumentDAO(Context context) {
        dbHelper = new DatabaseManager(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Document getResumeById(int documentId) {
        String selection = ID + " = " + documentId + " AND " + TYPE + " = " + Document.RESUME;
        Cursor cursor = database.query(TABLE_DOCUMENT, allColumns, selection, null, null, null, null);
        cursor.moveToFirst();
        Document document = cursorToDocument(cursor);
        cursor.close();

        return document;
    }

    public Document getCoverLetterById(int documentId) {
        String selection = ID + " = " + documentId + " AND " + TYPE + " = " + Document.COVER_LETTER;
        Cursor cursor = database.query(TABLE_DOCUMENT, allColumns, selection, null, null, null, null);
        cursor.moveToFirst();
        Document document = cursorToDocument(cursor);
        cursor.close();

        return document;
    }

    public Document addDocument(Document document) {
        ContentValues values = new ContentValues();
        values.put(TITLE, document.getTitle());
        values.put(FILENAME, document.getFileName());
        values.put(DESCRIPTION, document.getDescription());
        values.put(TYPE, document.getType());

        int insertId = (int) database.insert(TABLE_DOCUMENT, null, values);
        return document.getType() == Document.RESUME ? getResumeById(insertId) : getCoverLetterById(insertId);
    }

    public Document updateDocument(Document document) {
        ContentValues values = new ContentValues();
        values.put(TITLE, document.getTitle());
        values.put(FILENAME, document.getFileName());
        values.put(DESCRIPTION, document.getDescription());
        values.put(TYPE, document.getType());

        database.update(TABLE_DOCUMENT, values, ID + " = " + document.getId(), null);
        return document.getType() == Document.RESUME ? getResumeById(document.getId()) : getCoverLetterById(document.getId());
    }

    public void deleteDocument(Document document) {
        int id = document.getId();
        database.delete(TABLE_DOCUMENT, ID + " = " + id, null);
    }

    public List<Document> getAllResumes() {
        List<Document> documents = new ArrayList<Document>();

        String selection = TYPE + " = " + Document.RESUME;
        Cursor cursor = database.query(TABLE_DOCUMENT, allColumns, selection, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Document document = cursorToDocument(cursor);
            documents.add(document);
            cursor.moveToNext();
        }
        cursor.close();

        return documents;
    }

    private Document cursorToDocument(Cursor cursor) {
        Document document = new Document();
        document.setId(cursor.getInt(0));
        document.setTitle(cursor.getString(1));
        document.setFileName(cursor.getString(2));
        document.setDescription(cursor.getString(3));
        document.setType(cursor.getInt(4));

        return document;
    }
}