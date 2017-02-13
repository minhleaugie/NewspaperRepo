package edu.augustana.csc490.observerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.newspaperapp.R;

/**
 * Creates a menu for the different categories of articles and
 * allows user to obtain list of stories based on the selected category
 */
public class CategorySelector extends AppCompatActivity {
    // data fields
    ImageButton news, arts, features, opinions, sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_selector);

        // instantiate images and set click listeners
        // current images are just place holders until we get some
        // from Observer. We own no rights to these images.
        news= (ImageButton) findViewById(R.id.newsButton);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(1);
            }
        });

        arts = (ImageButton) findViewById(R.id.artsButton);
        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(2);
            }
        });

        features = (ImageButton) findViewById(R.id.featuresButton);
        features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(3);
            }
        });

        opinions = (ImageButton) findViewById(R.id.opinionsButton);
        opinions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(4);
            }
        });

        sports = (ImageButton) findViewById(R.id.sportsButton);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategory(5);
            }
        });

        // instantiate bottom nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        // set bottom nav listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent homeIntent = new Intent(CategorySelector.this, MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(homeIntent);
                        finish();
                        break;
                    case R.id.action_category:
                        break;
                    case R.id.action_search:
                        Intent searchIntent = new Intent(CategorySelector.this, Search.class);
                        searchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(searchIntent);
                        finish();
                        overridePendingTransition(R.anim.main_out, R.anim.main_in);
                        break;
                }
                return false;
            }
        });
    }

    /**
     * This method starts a new activity that displays the
     * articles based on the users category choice.
     * @param linkChoice
     */
    public void openCategory(int linkChoice) {
        Intent intent = new Intent(CategorySelector.this, CategoryArticleView.class);
        intent.putExtra(RssConstants.LINK, linkChoice);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
