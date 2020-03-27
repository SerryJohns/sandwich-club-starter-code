package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView imageIv;
    private TextView mainNameTv;
    private TextView descriptionTv;
    private TextView originTv;
    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        initUI();
        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void initUI() {
        imageIv = (ImageView) findViewById(R.id.image_iv);
        mainNameTv = (TextView) findViewById(R.id.main_name_tv);
        descriptionTv = (TextView) findViewById(R.id.description_tv);
        originTv = (TextView) findViewById(R.id.origin_tv);
        alsoKnownAsTv = (TextView) findViewById(R.id.also_known_as_tv);
        ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .into(imageIv);

        mainNameTv.setText(sandwich.getMainName());
        descriptionTv.setText(sandwich.getDescription());
        originTv.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getAlsoKnownAs().size() > 0) {
            // Get string representation of the list and remove brackets
            String alsoKnownAs = sandwich.getAlsoKnownAs()
                    .toString()
                    .replaceAll("(^\\[|]$)", "");
            alsoKnownAsTv.setText(alsoKnownAs);
        } else {
            alsoKnownAsTv.setText("N/A");
        }

        if (sandwich.getIngredients().size() > 0) {
            // Get string representation of the list and remove brackets
            String ingredients = sandwich.getIngredients()
                    .toString()
                    .replaceAll("(^\\[|]$)", "");
            ingredientsTv.setText(ingredients);
        } else {
            ingredientsTv.setText("N/A");
        }
    }
}
