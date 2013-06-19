package ru.translator;

import ru.translator.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QueryReceiver extends Activity {
	
	EditText input;
	Button translate;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_receiver);
        translate = (Button) findViewById(R.id.translate);        
        input = (EditText) findViewById(R.id.input_edit_text);
    }
    
    public void onClick(View v)
    {    	
		Intent intent = new Intent(this, QueryResults.class);
    	intent.putExtra("query", input.getText().toString());
    	startActivity(intent);
    }
}
