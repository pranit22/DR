package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dr.objects.Document;

public class DocumentDetailsActivity extends Activity {
    Document document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_details);

        Intent intent = getIntent();
        document = (Document) intent.getSerializableExtra("document");

        ((TextView) findViewById(R.id.title)).setText(document.getTitle());
        ((TextView) findViewById(R.id.fileName)).setText(document.getFileName());
        ((TextView) findViewById(R.id.description)).setText(document.getDescription());
        if(document.getType() == Document.RESUME) {
            ((TextView) findViewById(R.id.screenTitle)).setText("RESUME DETAILS");
        }
    }

    public void navigateToListDocuments(View view) {
        Intent intent = new Intent(this, DocumentsListActivity.class);
        if (document.getType() == document.RESUME) {
            intent.putExtra("resume", true);
        }
        startActivity(intent);
    }

    public void navigateToEditDocument(View view) {
        Intent intent = new Intent(this, EditCreateDocumentActivity.class);
        intent.putExtra("document", document);
        startActivity(intent);
    }
}
