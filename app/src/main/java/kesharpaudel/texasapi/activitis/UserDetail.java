package kesharpaudel.texasapi.activitis;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import kesharpaudel.texasapi.R;
import kesharpaudel.texasapi.adapter.RecyclerAdapter;
import kesharpaudel.texasapi.api.RetrofitClient;
import kesharpaudel.texasapi.models.ListDto;
import kesharpaudel.texasapi.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    String token;
    long loginId,customerId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        recyclerView=findViewById(R.id.recyclerview);
        token=getIntent().getExtras().getString("token");
        loginId=getIntent().getExtras().getLong("loginid");
        customerId=getIntent().getExtras().getLong("customerid");




        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mDrawerLayout=findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showUserDetail();



    }

    private void showUserDetail() {



        Call<ListDto> call= RetrofitClient.getInstance()
                .getApi().userlist(loginId,customerId,token);

        call.enqueue(new Callback<ListDto>() {
            @Override
            public void onResponse(Call<ListDto> call, Response<ListDto> response) {

                adapter=new RecyclerAdapter(response.body().getData(),UserDetail.this);

                if(response.isSuccessful()){
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<ListDto> call, Throwable t) {
                Toast.makeText(UserDetail.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
