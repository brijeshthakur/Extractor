package extract.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import extractor.ExtractRequest;
import extractor.exception.ExtractException;
import extractor.model.Errors;


public class ExtractExampleActivity extends AppCompatActivity implements ExtractRequest.ExtractResponseCallback<JSONObject> {

    private ProgressBar chatProgress;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_example);
        final EditText chatEditText = (EditText) findViewById(R.id.chat_text);
        Button btn_chat = (Button) findViewById(R.id.btn_extract);
        output = (TextView) findViewById(R.id.output);
        chatProgress = (ProgressBar) findViewById(R.id.chat_extract_progress);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ExtractRequest extractRequest = new ExtractRequest();
                String statement = chatEditText.getText().toString();
                if (TextUtils.isEmpty(statement)) {
                    return;
                }
                chatProgress.setVisibility(View.VISIBLE);
                output.setVisibility(View.GONE);
                try {
                    extractRequest.extract(statement, ExtractExampleActivity.this);
                } catch (ExtractException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onExtractComplete(final JSONObject response) throws JSONException {
        chatProgress.setVisibility(View.GONE);
        output.setVisibility(View.VISIBLE);
        output.setText(response.toString());
    }

    @Override
    public void onExtractError(final Errors.ExtractError error) {
        chatProgress.setVisibility(View.GONE);
        output.setVisibility(View.VISIBLE);
        output.setText(error.getErrorData());
    }
}
