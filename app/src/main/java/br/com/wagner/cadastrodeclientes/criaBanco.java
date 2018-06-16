package br.com.wagner.cadastrodeclientes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class criaBanco extends SQLiteOpenHelper {

    public static final String db = "clientes.db";
    public static final String TABELA = "clientes";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String TEL = "telefone";
    public static final String CPF = "cpf";
    public static final String DATA = "data";
    public static final String DES = "descricao";
    public static final String EMAIL = "email";
    public static final String OBS = "obs";
    private static final int VERSAO = 1;


    public criaBanco(Context context){
        super(context, db,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA +"("
                + ID + " integer primary key autoincrement,"
                + NOME + " VARCHAR(30),"
                + TEL + " VARCHAR(30),"
                + CPF + " VARCHAR(30),"
                + DATA + " DATE,"
                + DES + " VARCHAR(200),"
                + EMAIL + " VARCHAR(30)"
                //  + OBS + " VARCHAR(500)"
                +")";


        InputStream in = null;
        try {
            in = new FileInputStream(
                    new File(Environment.getDataDirectory()
                            + "/data/br.com.wagner.cadastrodeclientes/databases/clientes.db"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        boolean exists = (new File(String.valueOf(in))).exists();

        if (!exists) {

            db.execSQL(sql);
            db.execSQL("ALTER TABLE " + TABELA + " ADD COLUMN" + " obs"  + " VARCHAR(500)");

        }else{

            Backup();
            db.execSQL(sql);
            db.execSQL("ALTER TABLE " + TABELA + " ADD COLUMN" + " obs"  + " VARCHAR(500)");
            Restore();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        // If you need to add a column
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE clientes ADD COLUMN obs VARCHAR(500)");
        }
        //onCreate(db);
    }




    public void ApagarTudo( ) {


    }


    public void Restore () {
        // OutputStream out = null;

        try {

            final InputStream in = new FileInputStream(
                    new File(Environment.getExternalStorageDirectory()
                            + "/clientes.db"));

            // Caminho de Destino do Backup do Seu Banco de Dados


            OutputStream out = null;
            try {
                out = new FileOutputStream(new File(
                        Environment.getDataDirectory()
                                + "/data/br.com.wagner.cadastrodeclientes/databases/clientes.db"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] buf = new byte[1024];
            int len;

            try {

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                in.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

            try {

                out.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

            //Lista();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void Backup ( ){


        InputStream in = null;
        try {
            in = new FileInputStream(
                    new File(Environment.getDataDirectory()
                            + "/data/br.com.wagner.cadastrodeclientes/databases/clientes.db"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(
                    Environment.getExternalStorageDirectory()
                            + "/clientes.db"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
