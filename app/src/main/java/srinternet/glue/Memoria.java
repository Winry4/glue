package srinternet.glue;

/**
 * Created by JAVIER OLIVER ASUS on 27/07/2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import org.json.*;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Memoria {
    public static int sonidos, vibracion, notificaciones;
    public static String UserFBId, idSension, PrimNomFB, CityFB, GenderFB, GenderFind;
    public static int EdadUser,EdadMin,EdadMax, TotMensSingle, TotMensCupido, TotNotif, Modo;
    //Todo esto de JSONARRAY debe serializarse en objetos que para eso estmaos en JAVA coño
    public static JSONArray amigos, gente, genteTabla, bloqueados, ProductosDisp, ProductosComp, Notificaciones, Notifis, Fotos;
    public static Bitmap DataFotoPerfil;
    public static String nombrefotoPerfil;
    public static Bitmap [] PerfilFOt= new Bitmap [3];
    public static String [] PerfilFOtNom= new String [3];
    public static List<ListGrid> listGrids = new ArrayList<>();
    public static List<ListGrid> listGridsGluer=new ArrayList<>();
    public static List<People> peoplesT=new ArrayList<>();
    public static List<People> peoples=new ArrayList<>();
    public static List<OcultosClase>ocultosList=new ArrayList<>();
    public static List<AmigosGrid> Amigos=new ArrayList<>();
    public static Boolean vibra;
    public static Boolean notifi;
    public static Boolean sonido;
    public static JSONObject chatActual;
    public static List<Album> albumList=new ArrayList<>();
    public static List<Notifi> notifiList=new ArrayList<>();
    public static List<Princi> princiList = new ArrayList<>();
    public static List<Foto> fotosList=new ArrayList<>();
    public static int posicionFragment;
    public static int posicionFragmentFot;
    public static int ancho,alto,namigos;
    public static NotificacionesAdapter notiadapter;
    public static Bitmap fotoSel;
    public static ImageView fotoAct;
    public static FloatingActionButton botonAct;
    public static String accion;
    public static int PosAct;
    private static int edadMin;
    private static int edadMax;
    private static String modo,generoBus;
    public static AmigosGrid pareja1;
    public static AmigosGrid pareja2;
    public static int posiPareja1;
    public static int posiPareja2;
    public static int vuelta;
    public static int paginaACt;
    //public static Bitmap [] PerfilFOtUser= new Bitmap [4];
    public static String [] PerfilFOtNomUser= new String [4];
    public static List<History> historyList=new ArrayList<>();
    public static Programa_Principal.SectionsPagerAdapter mSectionsPagerAdapter;
    public static ViewPager mViewPager;
    public static String idFaceFotos;

    public static Bitmap hola;

    public static String idchatAct;
    public static Boolean clickVerFOtos=true;
    public static RecyclerView recy;
    public static MensajeAdapter ma;


    public static void cambiar(int min, int max, String mo, String gen){
        modo=mo;
        generoBus=gen;
        edadMin=min;
        edadMax=max;
    }


    public static String getCambios(){
        return "&modoEleg="+modo+"&generoBu="+generoBus+"&edadMini="+edadMin+"&edadMaxi="+edadMax;
    }
    public static void setAjustes(JSONObject ajus){
        try{
            sonidos = ajus.getJSONArray("ajustes").getJSONObject(0).getInt("SONIDOS");
            vibracion =ajus.getJSONArray("ajustes").getJSONObject(0).getInt("VIBRACIO");
            notificaciones = ajus.getJSONArray("ajustes").getJSONObject(0).getInt("NOTIFICA");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static Boolean compVersion(){
        String version="1.0.000 01";
        Boolean result=false;
        try {
            String response="";
            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter wr = new PrintWriter(
                    conn.getOutputStream());
            String params="funcion=f01&platafor=and&numVersi="+version;
            wr.print(params);

            wr.close();
            conn.connect();
            Scanner inStream = new Scanner(conn.getInputStream());
            while(inStream.hasNextLine())
                response+=(inStream.nextLine());

            conn.disconnect();
            result=new JSONObject(response).getString("estado").equals("O");
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return result;
    }

    public static void setTotal(JSONObject uva){
        try {

            TotMensSingle = uva.getJSONObject("totMens").getInt("TOTALA");
            TotMensCupido = uva.getJSONObject("totMens").getInt("TOTALB");
            TotNotif = uva.getJSONObject("totNotif").getInt("COUNT");
            System.out.println(TotNotif + " SSSSSSSSSSSSSSSSSSSSSSSSSS");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void setCambios(JSONObject log){
        try {

            UserFBId = log.getJSONArray("usuario").getJSONObject(0).getString("IDFACEBO");
            idSension = log.getJSONArray("usuario").getJSONObject(0).getString("IDSESION");
            PrimNomFB = log.getJSONArray("usuario").getJSONObject(0).getString("PRINOMFB");
            CityFB = log.getJSONArray("usuario").getJSONObject(0).getString("CIUDADFB");
            GenderFB = log.getJSONArray("usuario").getJSONObject(0).getString("GENEROFB");
            GenderFind = log.getJSONArray("usuario").getJSONObject(0).getString("GENEROBU");
            EdadUser =log.getJSONArray("usuario").getJSONObject(0).getInt("EDADUSER");
            EdadMin = log.getJSONArray("usuario").getJSONObject(0).getInt("EDADMINI");
            EdadMax = log.getJSONArray("usuario").getJSONObject(0).getInt("EDADMAXI");
            Modo = log.getJSONArray("usuario").getJSONObject(0).getInt("MODOELEG");
            gente= log.getJSONArray("gente");
            genteTabla = log.getJSONArray("genteTabla");
            /*

            */
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void setLogin(JSONObject log, final Activity ac){
        try {

            UserFBId = log.getJSONArray("usuario").getJSONObject(0).getString("IDFACEBO");
            idSension = log.getJSONArray("usuario").getJSONObject(0).getString("IDSESION");
            PrimNomFB = log.getJSONArray("usuario").getJSONObject(0).getString("PRINOMFB");
            CityFB = log.getJSONArray("usuario").getJSONObject(0).getString("CIUDADFB");
            GenderFB = log.getJSONArray("usuario").getJSONObject(0).getString("GENEROFB");
            GenderFind = log.getJSONArray("usuario").getJSONObject(0).getString("GENEROBU");
            EdadUser =log.getJSONArray("usuario").getJSONObject(0).getInt("EDADUSER");
            EdadMin = log.getJSONArray("usuario").getJSONObject(0).getInt("EDADMINI");
            EdadMax = log.getJSONArray("usuario").getJSONObject(0).getInt("EDADMAXI");
            Modo = log.getJSONArray("usuario").getJSONObject(0).getInt("MODOELEG");
            amigos = log.getJSONArray("amigos");
            gente= log.getJSONArray("gente");
            genteTabla = log.getJSONArray("genteTabla");
            Notificaciones = log.getJSONArray("notifiMostrar");
            sonidos = log.getJSONArray("ajustes").getJSONObject(0).getInt("SONIDOS");
            vibracion =log.getJSONArray("ajustes").getJSONObject(0).getInt("VIBRACIO");
            notificaciones = log.getJSONArray("ajustes").getJSONObject(0).getInt("NOTIFICA");
            Fotos=log.getJSONArray("foto");

            nombrefotoPerfil=log.getJSONArray("foto").getJSONObject(0).getString("FOTOGRAF");

			Amigos.clear();
            for(int i=0;i<amigos.length();i++){
                String n = amigos.getJSONObject(i).getString("PRINOMFB");
                String id = amigos.getJSONObject(i).getString("IDFACEBO");
                String filename = amigos.getJSONObject(i).getString("FOTOGRAF");

                AmigosGrid u = new AmigosGrid(n,filename,id, ac);
                Amigos.add(u);

            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
