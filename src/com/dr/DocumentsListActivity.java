package com.dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dr.objects.Document;
import com.dr.objects.dao.DocumentDAO;

import java.util.ArrayList;
import java.util.List;

public class DocumentsListActivity extends Activity {
    List<Document> documents;
    private DocumentDAO documentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_list);

        documentDAO = new DocumentDAO(this);
        documentDAO.open();
        documents = getIntent().hasExtra("resume") ? documentDAO.getAllResumes() : documentDAO.getAllCoverLetters();

        ListView listView = (ListView) findViewById(R.id.listView);

        DocumentsListAdapter adapter = new DocumentsListAdapter(this,
                R.layout.layout_document,
                (ArrayList<Document>) documents);

        listView.setAdapter(adapter);

        if (getIntent().hasExtra("resume")) {
            ((TextView) findViewById(R.id.screenTitle)).setText("RESUMES");
            ((Button) findViewById(R.id.newButton)).setText("NEW RESUME");
        }
    }

    class DocumentsListAdapter extends ArrayAdapter<Document> {

        private ArrayList<Document> items;

        public DocumentsListAdapter(Context context, int textViewResourceId, ArrayList<Document> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.layout_document, null);
            }
            final Document o = items.get(position);
            if (o != null) {
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView fileName = (TextView) v.findViewById(R.id.fileName);
                if (title != null) {
                    title.setText(o.getTitle());
                }
                if (fileName != null) {
                    fileName.setText(o.getFileName());
                }

                final Context context = getApplicationContext();
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DocumentDetailsActivity.class);
                        intent.putExtra("document", o);
                        startActivity(intent);
                    }
                });
            }
            return v;
        }
    }

    public void navigateToNewDocument(View view) {
        Intent intent = new Intent(this, EditCreateDocumentActivity.class);
        if (getIntent().hasExtra("resume")) {
            intent.putExtra("resume", true);
        }
        startActivity(new Intent(this, EditCreateDocumentActivity.class));
    }

    public void navigateToMainMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

}
