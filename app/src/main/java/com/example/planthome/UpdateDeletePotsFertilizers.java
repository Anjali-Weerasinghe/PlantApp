package com.example.planthome;

import android.view.Menu;
import android.view.MenuItem;

public interface UpdateDeletePotsFertilizers {
    void onBackPressed();

    boolean onCreateOptionsMenu(Menu menu);

    boolean onOptionsItemSelected(MenuItem item);

    @SuppressWarnings("StatementWithEmptyBody")
    boolean onNavigationItemSelected(MenuItem item);
}
