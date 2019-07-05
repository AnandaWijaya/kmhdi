package com.example.darsok.kmhdi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences mSharedPref;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mnav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mnav = (NavigationView) findViewById(R.id.nav_viewMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("berita");
        mDatabase.keepSynced(true);

        mBlogList = (RecyclerView) findViewById(R.id.myrecyclerView);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(mLayoutManager);


    }


    //search data
    private void firebaseSearch(String searchText) {

        //convert string entered in SearchView to lowercase
        String query = searchText.toLowerCase();

        Query firebaseSearchQuery = mDatabase.orderByChild("search").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                        Blog.class,
                        R.layout.row,
                        BlogViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                        viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDesc(), model.getImage());

                    }

                    @Override
                    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        BlogViewHolder blogViewHolder = super.onCreateViewHolder(parent, viewType);

                        blogViewHolder.setOnClickListener(new BlogViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //views
                                TextView mTitleTv = view.findViewById(R.id.post_title);
                                TextView mDescTv = view.findViewById(R.id.post_desc);
                                ImageView mImageView = view.findViewById(R.id.post_image);
                                //get data from view
                                String mTitle = mTitleTv.getText().toString();
                                String mDesc = mDescTv.getText().toString();
                                Drawable mDrawable = mImageView.getDrawable();
                                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                                // pass this data to new activity
                                Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("image", bytes);//put bitmap image as array of baytes
                                intent.putExtra("title", mTitle);//put title
                                intent.putExtra("desc", mDesc);//put description
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int posirion) {
                                //TODO do you own implementation on long  item Click
                            }
                        });

                        return blogViewHolder;
                    }
                };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (Blog.class, R.layout.row, BlogViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDesc(), model.getImage());
            }

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                BlogViewHolder blogViewHolder = super.onCreateViewHolder(parent, viewType);

                blogViewHolder.setOnClickListener(new BlogViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //views
                        TextView mTitleTv = view.findViewById(R.id.post_title);
                        TextView mDescTv = view.findViewById(R.id.post_desc);
                        ImageView mImageView = view.findViewById(R.id.post_image);
                        //get data from view
                        String mTitle = mTitleTv.getText().toString();
                        String mDesc = mDescTv.getText().toString();
                        Drawable mDrawable = mImageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();


                        // pass this data to new activity
                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("image", bytes);//put bitmap image as array of baytes
                        intent.putExtra("title", mTitle);//put title
                        intent.putExtra("desc", mDesc);//put description
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int posirion) {
                        //TODO do you own implementation on long  item Click
                    }
                });

                return blogViewHolder;
            }

        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_setting) {
            //TODO
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }
    }

