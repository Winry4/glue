package srinternet.glue;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class fotosPrincipal extends AppCompatActivity implements FragmentFotos.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PlaceholderFragment as;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    int nfotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nfotos=0;
        setContentView(R.layout.activity_fotos_principal);
        final ImageView punto1,punto2,punto3,punto4;
        punto1 = (ImageView)findViewById(R.id.punto_foto_1);
        punto2 = (ImageView)findViewById(R.id.punto_foto_2);
        punto3 = (ImageView)findViewById(R.id.punto_foto_3);
        punto4 = (ImageView)findViewById(R.id.punto_foto_4);

        nfotos = Memoria.peoples.get(Memoria.posicionFragment).getNumFotos();


        switch (nfotos){
            case 1:
                punto1.setVisibility(View.INVISIBLE);
                punto2.setVisibility(View.INVISIBLE);
                punto4.setVisibility(View.INVISIBLE);
                punto3.setImageResource(R.drawable.punto_a_fotos);
            case 2:
                punto1.setVisibility(View.INVISIBLE);
                punto4.setVisibility(View.INVISIBLE);
                punto2.setImageResource(R.drawable.punto_a_fotos);
            case 3:
                punto1.setVisibility(View.INVISIBLE);
                punto2.setImageResource(R.drawable.punto_a_fotos);
            case 4:
                punto1.setImageResource(R.drawable.punto_a_fotos);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                System.out.println(position);
                switch (position){
                    case 1:
                        if(nfotos == 2 || nfotos==3){
                            punto1.setImageResource(R.drawable.punto_b_fotos);
                            punto2.setImageResource(R.drawable.punto_b_fotos);
                            punto3.setImageResource(R.drawable.punto_a_fotos);
                            punto4.setImageResource(R.drawable.punto_b_fotos);
                        }else if(nfotos== 4) {
                            punto1.setImageResource(R.drawable.punto_b_fotos);
                            punto2.setImageResource(R.drawable.punto_a_fotos);
                            punto3.setImageResource(R.drawable.punto_b_fotos);
                            punto4.setImageResource(R.drawable.punto_b_fotos);
                        }
                        break;
                    case 0:
                        if(nfotos==1){
                            punto2.setImageResource(R.drawable.punto_a_fotos);
                        }else if(nfotos == 3 || nfotos == 2){
                            punto1.setImageResource(R.drawable.punto_b_fotos);
                            punto2.setImageResource(R.drawable.punto_a_fotos);
                            punto3.setImageResource(R.drawable.punto_b_fotos);
                            punto4.setImageResource(R.drawable.punto_b_fotos);
                        }else{
                            punto1.setImageResource(R.drawable.punto_a_fotos);
                            punto2.setImageResource(R.drawable.punto_b_fotos);
                            punto3.setImageResource(R.drawable.punto_b_fotos);
                            punto4.setImageResource(R.drawable.punto_b_fotos);
                        }
                        break;
                    case 2:
                        if(nfotos==3){
                            punto1.setImageResource(R.drawable.punto_b_fotos);
                            punto2.setImageResource(R.drawable.punto_b_fotos);
                            punto3.setImageResource(R.drawable.punto_b_fotos);
                            punto4.setImageResource(R.drawable.punto_a_fotos);
                        }else if(nfotos == 4){
                            punto1.setImageResource(R.drawable.punto_b_fotos);
                            punto2.setImageResource(R.drawable.punto_b_fotos);
                            punto3.setImageResource(R.drawable.punto_a_fotos);
                            punto4.setImageResource(R.drawable.punto_b_fotos);
                        }
                        break;
                    case 3:
                        punto1.setImageResource(R.drawable.punto_b_fotos);
                        punto2.setImageResource(R.drawable.punto_b_fotos);
                        punto3.setImageResource(R.drawable.punto_b_fotos);
                        punto4.setImageResource(R.drawable.punto_a_fotos);
                        break;

                }
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        ImageView f = (ImageView) findViewById(R.id.imageView2);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_fotos_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        static fotosPrincipal fp = new fotosPrincipal();
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public int getPos(){
            return pos;
        }
        public static int pos;
        public static Fragment newInstance(int sectionNumber) {

            Fragment fragment = null;

            for(int i = 0;i<Memoria.PerfilFOtNomUser.length;i++){
                if(i+1 == sectionNumber) {

                    System.out.println("SECTION NUMBer "+sectionNumber);
                    Memoria.posicionFragmentFot = sectionNumber;
                    fragment = new FragmentFotos();
                    fragment.onDetach();
                }
            }
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            return fragment;
        }
/*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_fragment_fotos, container, false);
            View v=inflater.inflate(R.layout.activity_fotos_principal,container,false);
            ImageView punto1,punto2,punto3,punto4;
            int nfotos=0;
            for(int i =0;i<Memoria.peoples.size();i++){
                if(i+1==Memoria.posicionFragment) nfotos=Memoria.peoples.get(i).getNumFotos();
            }

            punto1 = (ImageView)v.findViewById(R.id.punto_foto_1);
            punto2 = (ImageView)v.findViewById(R.id.punto_foto_2);
            punto3 = (ImageView)v.findViewById(R.id.punto_foto_3);
            punto4 = (ImageView)v.findViewById(R.id.punto_foto_4);

            switch (nfotos){
                case 1:
                    punto1.setVisibility(View.INVISIBLE);
                    punto2.setVisibility(View.INVISIBLE);
                    punto4.setVisibility(View.INVISIBLE);
                    punto3.setImageResource(R.drawable.punto_a_fotos);
                case 2:
                    punto1.setVisibility(View.INVISIBLE);
                    punto4.setVisibility(View.INVISIBLE);
                    punto2.setImageResource(R.drawable.punto_a_fotos);
                case 3:
                    punto1.setVisibility(View.INVISIBLE);
                    punto2.setImageResource(R.drawable.punto_a_fotos);
            }

            return rootView;
        }*/
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            int total=0;
            for(String s:Memoria.PerfilFOtNomUser){
                if(s!=null){
                    total++;
                }
            }
            return total;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            for(int i = 0;i<Memoria.PerfilFOtNomUser.length;i++){
                if(position == i) {
                    return "SECTION " + i+1;
                }
            }
            return null;
        }
    }
}
