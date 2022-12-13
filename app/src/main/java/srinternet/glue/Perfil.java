package srinternet.glue;

        import android.content.ClipData;
        import android.content.ClipDescription;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
        import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.DragEvent;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.PrintWriter;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Scanner;
        import java.util.Timer;
        import java.util.TimerTask;

public class Perfil extends AppCompatActivity {

    List<BasisGrid> basisGrids = new ArrayList<>();
    GridView gridView;
    ClipData dragData;
    int position;
    int dragPosition;
    Object current = null;
    RelativeLayout screenLayout;
    public static final String TAG = "drag_drop";
    Timer timer;
    int yLocation;
    GridAdapter gridAdapter;
    Button button1;

    public void guardar(View view){

        TextView h = (TextView) findViewById(R.id.guardar);
        h.setVisibility(View.INVISIBLE);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter(
                            conn.getOutputStream());
                    String params = "funcion=f33&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&posici01="+ basisGrids.get(0).getImageName() +"&posici02="+ basisGrids.get(1).getImageName() +"&posici03="+ basisGrids.get(2).getImageName() +"&posici04=" +basisGrids.get(3).getImageName();
                    wr.print(params);
                    wr.close();
                    conn.connect();
                    String res = "";
                    Scanner Stream = new Scanner(conn.getInputStream());
                    while (Stream.hasNextLine())
                        res += (Stream.nextLine());

                    conn.disconnect();

                    String acion=new JSONObject(res).getString("accion");

                    if(acion.equals("guardar")){
                        HttpURLConnection con = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        PrintWriter wr1 = new PrintWriter(
                                con.getOutputStream());
                        String param = "funcion=f11&idFacebo=" + Memoria.UserFBId + "&idSesion=" + Memoria.idSension + "&posici01="+ basisGrids.get(0).getImageName() +"&posici02="+ basisGrids.get(1).getImageName() +"&posici03="+ basisGrids.get(2).getImageName() +"&posici04=" +basisGrids.get(3).getImageName();
                        wr1.print(param);
                        wr1.close();
                        con.connect();
                        String re = "";
                        Scanner Strea = new Scanner(con.getInputStream());
                        while (Strea.hasNextLine())
                            re += (Strea.nextLine());

                        Memoria.Fotos=new JSONObject(re).getJSONArray("fotos");

                    }
                    //tras comprobacons e sube el orden y a chuparla
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String [] imageNa= new String [4];
        imageNa[0]=Memoria.nombrefotoPerfil;
        imageNa[1]=Memoria.PerfilFOtNom[0];
        imageNa[2]=Memoria.PerfilFOtNom[1];
        imageNa[3]=Memoria.PerfilFOtNom[2];
        basisGrids.clear();
        for (int i = 0; i < 4; i++) {
            BasisGrid bg = new BasisGrid(imageNa[i]);
            basisGrids.add(bg);
        }
        screenLayout = (RelativeLayout) findViewById(R.id.screenLayout);
        gridView = (GridView) findViewById(R.id.gridImage);
        gridAdapter = new GridAdapter(Perfil.this, basisGrids);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        // ImageView im = (ImageView) findViewById(R.id.imageView34);
        // im.setImageBitmap(Memoria.DataFotoPerfil);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bitmap originalBitmap = Memoria.DataFotoPerfil;


        ImageView img=(ImageView)findViewById(R.id.img_btn_atras);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });




        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        ImageView imageView = (ImageView) findViewById(R.id.imageView34);
        //button1=(Button) findViewById(R.id.grid_item_btn);

        imageView.setImageDrawable(roundedDrawable);


        String [] imageNa= new String [4];
        imageNa[0]=Memoria.nombrefotoPerfil;
        imageNa[1]=Memoria.PerfilFOtNom[0];
        imageNa[2]=Memoria.PerfilFOtNom[1];
        imageNa[3]=Memoria.PerfilFOtNom[2];
        for (int i = 0; i < 4; i++) {
            BasisGrid bg = new BasisGrid(imageNa[i]);
            basisGrids.add(bg);

        }
        screenLayout = (RelativeLayout) findViewById(R.id.screenLayout);
        gridView = (GridView) findViewById(R.id.gridImage);
        gridAdapter = new GridAdapter(Perfil.this, basisGrids);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        gridView.setLongClickable(true);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @SuppressWarnings("deprecation")

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int p, long id) {
                position = p;
                current = view;
                if (position > AdapterView.INVALID_POSITION) {

                    ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                    dragData = new ClipData((CharSequence) view.getTag(),
                            new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);
                    view.startDrag(dragData,
                            myShadow,
                            null,
                            0);
                    screenLayout.setOnDragListener(new View.OnDragListener() {
                        @Override
                        public boolean onDrag(View v, DragEvent dragEvent) {
                            v = (View) current;
                            boolean result = true;
                            int action = dragEvent.getAction();
                            switch (action) {
                                case DragEvent.ACTION_DRAG_STARTED:
                                    break;
                                case DragEvent.ACTION_DRAG_ENTERED:

                                    break;
                                case DragEvent.ACTION_DRAG_EXITED:

                                    break;
                                case DragEvent.ACTION_DROP:
                                    int dropX = (int) dragEvent.getX();
                                    int dropY = (int) dragEvent.getY()- (int)findViewById(R.id.appbar).getHeight();

                                    dragPosition = gridView.pointToPosition(dropX, dropY);
                                    try{
                                        if (dragEvent.getLocalState() == v) {
                                            return false;
                                        } else {
                                            BasisGrid pickImage = basisGrids.get(position);
                                            BasisGrid pickPlace = basisGrids.get(dragPosition);
                                            basisGrids.remove(position);
                                            basisGrids.add(position, pickPlace);
                                            basisGrids.remove(dragPosition);
                                            basisGrids.add(dragPosition, pickImage);


                                            gridAdapter = new GridAdapter(Perfil.this, basisGrids);
                                            gridView.setAdapter(gridAdapter);

                                            gridAdapter.notifyDataSetChanged();

                                            TextView h = (TextView) findViewById(R.id.guardar);
                                            h.setVisibility(View.VISIBLE);



                                        }
                                        break;
                                    }catch (Exception e){

                                    }

                                case DragEvent.ACTION_DRAG_ENDED:
                                    break;
                                case DragEvent.ACTION_DRAG_LOCATION:
                                    yLocation = (int) dragEvent.getY();
                                    break;
                                default:
                                    result = false;
                                    break;
                            }
                            return result;
                        }
                    });
                }
                return false;
            }
        });
    }



}
