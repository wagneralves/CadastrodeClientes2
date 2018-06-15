package br.com.wagner.cadastrodeclientes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;


import static br.com.wagner.cadastrodeclientes.criaBanco.TABELA;


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
        valores.put(criaBanco.NOME, nome);
        valores.put(criaBanco.TEL, telefone);
        valores.put(criaBanco.CPF, cpf);
        valores.put(criaBanco.DATA, data);
        valores.put(criaBanco.DES, descricao);
        valores.put(criaBanco.EMAIL, email);
        valores.put(criaBanco.OBS, obs);

        resultado = db.insert(TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }


    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.NOME,banco.TEL,banco.CPF,banco.DATA,banco.DES,banco.EMAIL,banco.OBS};
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
        String[] campos =  {banco.ID,banco.NOME,banco.TEL,banco.CPF,banco.DATA,banco.DES,banco.EMAIL,banco.OBS};
        String where = criaBanco.ID + "=" + id;
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
        where = criaBanco.ID + "=" + id;
        valores = new ContentValues();
        valores.put(criaBanco.NOME, nome);
        valores.put(criaBanco.TEL, telefone);
        valores.put(criaBanco.CPF, cpf);
        valores.put(criaBanco.DATA, data);
        valores.put(criaBanco.DES, descricao);
        valores.put(criaBanco.EMAIL, email);
        valores.put(criaBanco.OBS, obs);


        db.update(TABELA,valores,where,null);
        db.close();
    }


    public void deletaRegistro(int id){
        String where = criaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(TABELA,where,null);
        db.close();
    }



}