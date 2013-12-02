package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.objects.Document;
import com.dr.objects.dao.DocumentDAO;

public class EditCreateDocumentActivity extends Activity {
    private DocumentDAO documentDAO;
    private Document document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_document);

        documentDAO = new DocumentDAO(this);
        documentDAO.open();

        Intent intent = getIntent();
        if(intent.hasExtra("resume")) {
            ((TextView)findViewById(R.id.screenTitle)).setText("EDIT/CREATE RESUME");
        }
        if (intent.hasExtra("document")) {

            document = (Document) intent.getSerializableExtra("document");
            ((EditText) findViewById(R.id.title)).setText(document.getTitle());
            ((EditText) findViewById(R.id.fileName)).setText(document.getFileName());
            ((EditText) findViewById(R.id.description)).setText(document.getDescription());

            Button button = (Button) findViewById(R.id.submit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    document.setTitle(((EditText) findViewById(R.id.title)).getText().toString());
                    document.setFileName(((EditText) findViewById(R.id.fileName)).getText().toString());
                    document.setDescription(((EditText) findViewById(R.id.description)).getText().toString());

                    document = documentDAO.updateDocument(document);
                    String message = "Document " + document.getId() + " updated successfully!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), DocumentDetailsActivity.class);
                    intent.putExtra("document", document);
                    startActivity(intent);
                }
            });
        }
    }

    public void submitAddEditDocument(View view) {
        document = new Document();
        document.setType(getIntent().hasExtra("resume") ? Document.RESUME : Document.COVER_LETTER);
        document.setTitle(((EditText) findViewById(R.id.title)).getText().toString());
        document.setFileName(((EditText) findViewById(R.id.fileName)).getText().toString());
        document.setDescription(((EditText) findViewById(R.id.description)).getText().toString());

        document = documentDAO.addDocument(document);
        String message = "Document " + document.getId() + " saved successfully!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), DocumentDetailsActivity.class);
        intent.putExtra("document", document);
        startActivity(intent);
    }

    public void finishActivity(View view) {
        finish();
    }

}
