package br.com.wagner.cadastrodeclientes;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class Inserir extends Activity {

    EditText txtNome, txtEmail, txtTelefone, txtDescricao, txtCPF, edtObs;
    TextView txvData;
    SQLiteDatabase db;
    ContentValues ctv;
    Intent it;

    Calendar c = Calendar.getInstance();
    TextView display;
    int cday, cmonth, cyear;
    ImageView chooseDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserir);

        ImageView changeDate = (ImageView) findViewById(R.id.chooseDateButton);
        display = (TextView) findViewById(R.id.txvDate);

        changeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Inserir.this, d,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    DatePickerDialog.OnDateSetListener d = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            cday = dayOfMonth;
            cmonth = monthOfYear + 1;
            cyear = year;

            display.setText(cday + "/" + cmonth + "/"
                    + cyear);
        }
    };


    public void CadastrarClick(View v) {

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtCPF = (EditText) findViewById(R.id.txtCpf);
        txvData = (TextView) findViewById(R.id.txvDate);
        edtObs = (EditText) findViewById(R.id.edtObs);

        if (txtNome.getText().toString().length() < 4) {

            mensagemExibir(getString(R.string.javaAlerta), getString(R.string.javaNomeTeste));
            txtNome.requestFocus();

        } else {
            if (txtTelefone.getText().toString().length() < 8) {

                mensagemExibir(getString(R.string.javaAlerta), getString(R.string.javaTelefoneTeste));
                txtTelefone.requestFocus();

            } else {

                try {

                    db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);

                    ctv = new ContentValues();
                    ctv.put("nome", txtNome.getText().toString());
                    ctv.put("telefone", txtTelefone.getText().toString());
                    ctv.put("email", txtEmail.getText().toString());
                    ctv.put("descricao", txtDescricao.getText().toString());
                    ctv.put ("cpf", txtCPF.getText().toString());
                    ctv.put ("data", txvData.getText().toString());
                    ctv.put ("obs", edtObs.getText().toString());

                    if (db.insert("clientes", "_id", ctv) > 0) {

                        mensagemExibir(getString(R.string.javaOK), txtNome.getText() + getString(R.string.javaVadastradoSucesso));
                        chamaListar();

                    }

                } catch (Exception erro) {
                    mensagemExibir(getString(R.string.javaErro), getString(R.string.javaFalhaCadastrar) + erro.getMessage());
                }
            }
        }
    }

    //Mensagem para exibir exceções com parametros
    public void mensagemExibir (String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(Inserir.this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton(getString(R.string.javaOK), null);
        mensagem.show();

    }

   public void ListaClientes(View v){

        it = new Intent(getBaseContext(), ListarActivity.class);
        startActivity(it);
    }

    public void chamaListar() {

        it = new Intent(getBaseContext(), ListarActivity.class);
        startActivity(it);
    }


}
