package br.com.wagner.cadastrodeclientes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import static br.com.wagner.cadastrodeclientes.DBTableConfig.TABELA;




class bancoController {


    private SQLiteDatabase db;
    private criaBanco banco;
    SQLiteCursor c;
    Intent it;


    public bancoController(Context context){
        banco = new criaBanco(context);
    }

    public String insereDado(String nome, String telefone, String cpf, String data, String descricao, String email, String obs) {
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(DBTableConfig.Columns.NOME, nome);
        valores.put(DBTableConfig.Columns.TEL, telefone);
        valores.put(DBTableConfig.Columns.CPF, cpf);
        valores.put(DBTableConfig.Columns.DATA, data);
        valores.put(DBTableConfig.Columns.DES, descricao);
        valores.put(DBTableConfig.Columns.EMAIL, email);
        valores.put(DBTableConfig.Columns.OBS, obs);

        resultado = db.insert(TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }


    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {DBTableConfig.Columns.ID,DBTableConfig.Columns.NOME,DBTableConfig.Columns.TEL,DBTableConfig.Columns.CPF,DBTableConfig.Columns.DATA,DBTableConfig.Columns.DES,DBTableConfig.Columns.EMAIL,DBTableConfig.Columns.OBS};
        db = banco.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM clientes ORDER BY nome ", null);
        //cursor = db.query(banco.TABELA, campos, null, null, null, null, null );

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {DBTableConfig.Columns.ID,DBTableConfig.Columns.NOME,DBTableConfig.Columns.TEL,DBTableConfig.Columns.CPF,DBTableConfig.Columns.DATA,DBTableConfig.Columns.DES,DBTableConfig.Columns.EMAIL,DBTableConfig.Columns.OBS};
        String where = DBTableConfig.Columns.ID + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(TABELA,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, String nome, String telefone, String cpf, String data, String descricao, String email, String obs){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = DBTableConfig.Columns.ID + "=" + id;
        valores = new ContentValues();
        valores.put(DBTableConfig.Columns.NOME, nome);
        valores.put(DBTableConfig.Columns.TEL, telefone);
        valores.put(DBTableConfig.Columns.CPF, cpf);
        valores.put(DBTableConfig.Columns.DATA, data);
        valores.put(DBTableConfig.Columns.DES, descricao);
        valores.put(DBTableConfig.Columns.EMAIL, email);
        valores.put(DBTableConfig.Columns.OBS, obs);


        db.update(TABELA,valores,where,null);
        db.close();
    }


    public void deletaRegistro(int id){
        String where = DBTableConfig.Columns.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(TABELA,where,null);
        db.close();
    }



}