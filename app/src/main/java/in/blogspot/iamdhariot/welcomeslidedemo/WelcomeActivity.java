package in.blogspot.iamdhariot.welcomeslidedemo;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mPager;
    private int[] layouts = {R.layout.firstslide,R.layout.secondslide,R.layout.thirdslide};
    private Button btnSkip,btnNext;
    private MPagerAdapter mPagerAdapter;
    private LinearLayout dots_layout;
    private ImageView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(new PreferenceManager(this).checkPreference()){
            finish();
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));

        }

        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);
        mPager = (ViewPager)findViewById(R.id.viewPager);
        btnNext = (Button)findViewById(R.id.btnNext);
        dots_layout = (LinearLayout)findViewById(R.id.dotLayout);
        btnSkip = (Button)findViewById(R.id.btnSkip);
        mPagerAdapter = new MPagerAdapter(layouts,this);
        mPager.setAdapter(mPagerAdapter);
        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        createDots(0);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1){
                    btnNext.setText("Start");
                    btnSkip.setVisibility(View.INVISIBLE);


                }else{
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
    private void createDots(int current_position){
        if(dots_layout!=null){

            dots_layout.removeAllViews();

            dots = new ImageView[layouts.length];

            for(int i=0;i<layouts.length;i++){
                dots[i] = new ImageView(this);
                if(i==current_position){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));

                }
                else{
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                dots_layout.addView(dots[i],params);

            }


        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnNext:
                loadNextSlide();
                break;
            case R.id.btnSkip:
                new PreferenceManager(this).writePreference();
                finish();
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                break;


        }

    }
    private void loadNextSlide(){
        int next_slide = mPager.getCurrentItem()+1;
        if(next_slide<layouts.length){
            mPager.setCurrentItem(next_slide);

        }
        else{
            finish();
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            new PreferenceManager(this).writePreference();
        }
    }
}
